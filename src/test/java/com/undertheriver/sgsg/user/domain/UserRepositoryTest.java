package com.undertheriver.sgsg.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.undertheriver.sgsg.common.type.UserRole;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @DisplayName("유저의 포스팅 패스워드는 단방향 해로 암호화된다.")
    @Test
    void name() {
        String rawPassword = "1234";
        User user = User.builder()
            .name("김홍빈")
            .userRole(UserRole.USER)
            .profileImageUrl("http://naver.com/test.png")
            .userSecretMemoPassword("1234")
            .email("fusis1@naver.com")
            .build();

        Long userId = repository.save(user).getId();
        User persistedUser = repository.findById(userId).get();
        String actual = persistedUser.getUserSecretFolderPassword().getPassword();

        assertAll(
            () -> assertThat(bCryptPasswordEncoder.matches(rawPassword, actual)).isTrue(),
            () -> assertThat(actual).isNotEqualTo("1234")
        );
    }
}