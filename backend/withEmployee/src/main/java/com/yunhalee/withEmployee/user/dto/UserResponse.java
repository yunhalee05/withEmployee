package com.yunhalee.withEmployee.user.dto;

import com.yunhalee.withEmployee.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

    private Integer id;
    private String name;
    private String email;
    private String description;
    private String imageUrl;
    private String phoneNumber;
    private List<UserTeamResponse> teams;
    private List<UserCompanyResponse> companies;
    private String role;

    private UserResponse(User user, List<UserTeamResponse> teams, List<UserCompanyResponse> companies) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.description = user.getDescription();
        this.imageUrl = user.getImageUrl();
        this.phoneNumber = user.getPhoneNumber();
        this.teams = teams;
        this.companies = companies;
        this.role = user.getRole();
    }

    public static UserResponse of(User user, List<UserTeamResponse> teams,
        List<UserCompanyResponse> companies) {
        return new UserResponse(user, teams, companies);
    }

    public static UserResponse of(User user) {
        return new UserResponse(user, new ArrayList<>(), new ArrayList<>());
    }

}
