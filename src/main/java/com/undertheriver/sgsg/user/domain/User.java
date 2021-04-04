package com.undertheriver.sgsg.user.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private UserSecretMemoPassword userSecretMemoPassword;

	public User(String userSecretMemoPassword) {
		this.userSecretMemoPassword = UserSecretMemoPassword.from(userSecretMemoPassword);
	}
}
