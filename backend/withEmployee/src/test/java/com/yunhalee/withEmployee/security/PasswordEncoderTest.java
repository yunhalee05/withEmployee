package com.yunhalee.withEmployee.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {

    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "12345";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        assertThat(matches).isTrue();
    }
}
