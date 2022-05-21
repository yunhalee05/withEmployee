package com.yunhalee.withEmployee.team.dto;

import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamResponse {


    private Integer id;
    private String name;
    private String company;
    private List<SimpleUserResponse> users;
    private Integer companyId;

    private TeamResponse(Team team, List<SimpleUserResponse> users) {
        this.id = team.getId();
        this.name = team.getName();
        this.company = team.getCompanyName();
        this.users = users;
        this.companyId = team.getCompanyId();
    }

    public static TeamResponse of(Team team, List<SimpleUserResponse> users) {
        return new TeamResponse(team, users);
    }
}
