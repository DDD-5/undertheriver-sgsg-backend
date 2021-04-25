package com.undertheriver.sgsg.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.undertheriver.sgsg.common.dto.CurrentUser;
import com.undertheriver.sgsg.user.domain.User;
import com.undertheriver.sgsg.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public CurrentUser findById(Long id) {
		return userRepository.findById(id)
			.map(this::toDto)
			.orElseThrow(IllegalAccessError::new);
	}

	private CurrentUser toDto(User user) {
		return CurrentUser.builder()
			.id(user.getId())
			.name(user.getName())
			.profileImageUrl(user.getProfileImageUrl())
			.email(user.getEmail())
			.userRole(user.getUserRole())
			.build();
	}
}
