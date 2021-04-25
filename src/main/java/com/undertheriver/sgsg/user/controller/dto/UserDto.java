package com.undertheriver.sgsg.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	static public class DetailResponse {
		private String name;
		private String email;
		private String profileImageUrl;
	}
}
