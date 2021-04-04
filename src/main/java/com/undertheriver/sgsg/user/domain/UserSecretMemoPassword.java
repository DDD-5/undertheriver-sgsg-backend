package com.undertheriver.sgsg.user.domain;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserSecretMemoPassword {

	@Convert(converter = PasswordConverter.class)
	private String password;

	private UserSecretMemoPassword(String password) {
		this.password = password;
	}

	public static UserSecretMemoPassword from(String password) {
		return new UserSecretMemoPassword(password);
	}
}
