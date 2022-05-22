package com.yunhalee.withEmployee.user.dto;

import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleUserResponse {

    private Integer id;
    private String name;
    private String phoneNumber;
    private String role;
    private String email;
    private String imageUrl;
    private String description;


    private SimpleUserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        this.description = user.getDescription();
    }

    public static SimpleUserResponse of(User user) {
        return new SimpleUserResponse(user);
    }

}
