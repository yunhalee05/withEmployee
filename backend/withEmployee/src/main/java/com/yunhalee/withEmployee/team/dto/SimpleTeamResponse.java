package com.yunhalee.withEmployee.team.dto;

import com.yunhalee.withEmployee.company.dto.SimpleCompanyResponse;
import com.yunhalee.withEmployee.team.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleTeamResponse {

    private Integer id;
    private String name;
    private Integer totalNumber;
    private SimpleCompanyResponse company;

    public SimpleTeamResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.totalNumber = team.getTotalNumber();
    }

    public SimpleTeamResponse(Team team, SimpleCompanyResponse company) {
        this.id = team.getId();
        this.name = team.getName();
        this.totalNumber = team.getTotalNumber();
        this.company = company;
    }

    public static SimpleTeamResponse of(Team team) {
        return new SimpleTeamResponse(team);
    }

    public static SimpleTeamResponse of(Team team, SimpleCompanyResponse company) {
        return new SimpleTeamResponse(team, company);
    }
}
