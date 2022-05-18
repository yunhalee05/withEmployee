package com.yunhalee.withEmployee.user.dto;

import com.yunhalee.withEmployee.team.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTeamResponse {

    private Integer id;

    private String name;

    private String company;

    private UserTeamResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.company = team.getCompanyName();
    }

    public static UserTeamResponse of(Team team) {
        return new UserTeamResponse(team);
    }

}
