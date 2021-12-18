package com.yunhalee.withEmployee.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {

    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawpassword = "12345";
        String encodedPassword = passwordEncoder.encode(rawpassword);
        System.out.println(encodedPassword);

        boolean matches = passwordEncoder.matches(rawpassword, encodedPassword);

        assertThat(matches).isTrue();
    }
}
