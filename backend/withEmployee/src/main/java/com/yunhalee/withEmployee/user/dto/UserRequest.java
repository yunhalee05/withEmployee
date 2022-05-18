package com.yunhalee.withEmployee.user.dto;

import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String id;
    private String name;
    private String email;
    private String password;
    private String description;
    private String phoneNumber;
    private boolean isCEO;

    public UserRequest(String id, String name, String email, String password, String description, String phoneNumber, boolean isCEO) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.isCEO = isCEO;
    }

    public UserRequest(String name, String email, String password, String description,
        String phoneNumber, boolean isCEO) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.isCEO = isCEO;
    }

    public User toUser(String encodedPassword) {
        if (isCEO) {
            return User.of(name, email, encodedPassword, description, phoneNumber, Role.CEO);
        }
        return User.of(name, email, encodedPassword, description, phoneNumber, Role.MEMBER);
    }
}
