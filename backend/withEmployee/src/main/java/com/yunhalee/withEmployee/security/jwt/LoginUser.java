package com.yunhalee.withEmployee.security.jwt;

import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUser {

    private Integer id;
    private String email;
    private Role role;

    public LoginUser(Integer id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public static LoginUser of(User user) {
        return new LoginUser(user.getId(), user.getEmail(), Role.valueOf(user.getRole()));
    }
}
