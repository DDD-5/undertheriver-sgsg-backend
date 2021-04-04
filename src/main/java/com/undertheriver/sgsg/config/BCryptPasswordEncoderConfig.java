package com.undertheriver.sgsg.config;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptPasswordEncoderConfig {

	@Value("auth.encrypt.seed")
	private String encryptSeed;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(4, new SecureRandom(encryptSeed.getBytes(StandardCharsets.UTF_8)));
	}
}
