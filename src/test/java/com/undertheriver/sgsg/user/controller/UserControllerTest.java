package com.undertheriver.sgsg.user.controller;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import com.undertheriver.sgsg.auth.common.JwtProvider;
import com.undertheriver.sgsg.common.type.UserRole;
import com.undertheriver.sgsg.user.controller.dto.UserDto;
import com.undertheriver.sgsg.user.domain.User;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/user.sql")
class UserControllerTest {

	@LocalServerPort
	protected int port;

	@Autowired
	private JwtProvider jwtProvider;

	private String token;

	@BeforeEach
	void setUp() {
		User user = User.builder()
			.email("test@test.com")
			.profileImageUrl("http://naver.com/adf.png")
			.userRole(UserRole.USER)
			.name("TEST")
			.userSecretMemoPassword("1234")
			.build();

		token = jwtProvider.createToken(1L, user.getUserRole());

		if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
			RestAssured.port = port;
		}
	}

	@DisplayName("유저 정보 조회")
	@Test
	void name() {
		//@formatter:off
		System.out.println(token);
		UserDto.DetailResponse userDetail = given()
		.when()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.auth()
				.oauth2(token)
			.get("/api/v1/users/me")
		.then()
			.statusCode(HttpStatus.SC_OK)
			.log()
				.all()
			.extract()
				.as(UserDto.DetailResponse.class);
		//@formatter:on

		assertAll(
			() -> assertThat(userDetail.getName()).isEqualTo("TEST"),
			() -> assertThat(userDetail.getEmail()).isEqualTo("test@test.com"),
			() -> assertThat(userDetail.getProfileImageUrl()).isEqualTo("http://naver.com/adf.png")
		);
	}
}