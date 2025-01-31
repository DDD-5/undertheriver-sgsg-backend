package com.undertheriver.sgsg.config.security.handler;

import static com.undertheriver.sgsg.config.security.HttpCookieOAuth2AuthorizationRequestRepository.*;
import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.undertheriver.sgsg.auth.common.JwtProvider;
import com.undertheriver.sgsg.common.exception.BadRequestException;
import com.undertheriver.sgsg.common.type.UserRole;
import com.undertheriver.sgsg.config.AppProperties;
import com.undertheriver.sgsg.config.security.HttpCookieOAuth2AuthorizationRequestRepository;
import com.undertheriver.sgsg.config.security.UserPrincipal;
import com.undertheriver.sgsg.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final JwtProvider jwtProvider;
    private final List<URI> allowRedirectUris;

    public RestAuthenticationSuccessHandler(
        HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository,
        JwtProvider jwtProvider,
        AppProperties appProperties
    ) {
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        this.jwtProvider = jwtProvider;
        this.allowRedirectUris = appProperties.getOauth2()
            .getAuthorizedRedirectUris()
            .stream()
            .map(URI::create)
            .collect(toList());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) {

        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
            .map(Cookie::getValue);

        if (redirectUri.isPresent() && isNotSameHost(redirectUri.get())) {
            throw new BadRequestException("승인되지 않은 URI입니다");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();

        String token = fetchToken(principal);
        return UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("token", token)
            .build()
            .toUriString();
    }

    private String fetchToken(UserPrincipal principal) {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)principal.getAuthorities();
        String authority = authorities.stream()
            .findFirst()
            .orElse(new SimpleGrantedAuthority(UserRole.USER.name()))
            .getAuthority();

        return jwtProvider.createToken(principal.getId(), UserRole.from(authority));
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isNotSameHost(String redirectUrl) {
        URI clientRedirectUri = URI.create(redirectUrl);
        return allowRedirectUris.stream()
            .noneMatch(allowUri ->
                allowUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                    && allowUri.getPort() == clientRedirectUri.getPort()
            );
    }
}
