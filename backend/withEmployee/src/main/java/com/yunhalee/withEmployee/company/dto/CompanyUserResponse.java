package com.yunhalee.withEmployee.company.dto;

import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyUserResponse {

    private Integer id;
    private String role;
    private String name;
    private String email;

    private CompanyUserResponse(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public static CompanyUserResponse of(User user) {
        return new CompanyUserResponse(user);
    }
}
