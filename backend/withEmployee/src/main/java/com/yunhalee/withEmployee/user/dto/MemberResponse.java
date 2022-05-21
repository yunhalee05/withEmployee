package com.yunhalee.withEmployee.user.dto;

import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {

    private Integer id;
    private String role;
    private String name;
    private String email;

    private MemberResponse(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public static MemberResponse of(User user) {
        return new MemberResponse(user);
    }
}
