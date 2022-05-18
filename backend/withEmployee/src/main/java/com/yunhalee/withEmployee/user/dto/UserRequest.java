package com.yunhalee.withEmployee.user.dto;

import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private String description;
    private String phoneNumber;
    private boolean ceo;

    public UserRequest(String name, String email, String password, String description, String phoneNumber, boolean ceo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.ceo = ceo;
    }

    public User toUser(String encodedPassword) {
        if (ceo) {
            return User.of(name, email, encodedPassword, description, phoneNumber, Role.CEO);
        }
        return User.of(name, email, encodedPassword, description, phoneNumber, Role.MEMBER);
    }
}
