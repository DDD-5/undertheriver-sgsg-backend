package com.undertheriver.sgsg.config.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.undertheriver.sgsg.auth.common.JwtProvider;
import com.undertheriver.sgsg.common.exception.AccessTokenLoadException;
import com.undertheriver.sgsg.config.security.UserPrincipal;
import com.undertheriver.sgsg.user.domain.User;
import com.undertheriver.sgsg.user.domain.UserRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			String jwt = getJwtFromRequest(request);
			Claims claims = jwtProvider.extractValidSubject(jwt);
			String userId = claims.get("userId", String.class);

			UserDetails userDetails = createUserDetails(userId);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
				null, userDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
			request.setAttribute("userId", userId);

			// TODO 토큰이 만료됐을때 액션 취할 수 있는지
		} catch (IllegalArgumentException | AccessTokenLoadException e) {
			logger.info("인증에 실패했습니다.");
		}

		filterChain.doFilter(request, response);
	}

	private UserDetails createUserDetails(String userId) {
		User user = userRepository.findById(Long.valueOf(userId))
			.orElseThrow(IllegalArgumentException::new);

		UserDetails userDetails = UserPrincipal.create(user);
		return userDetails;
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		throw new IllegalArgumentException("보안정보가 없습니다.");
	}
}
