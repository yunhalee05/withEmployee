package com.yunhalee.withEmployee.team.dto;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.team.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamRequest {

    private String name;
    private Integer companyId;

    public TeamRequest(String name, Integer companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public Team toTeam(Company company){
        return Team.of(this.name, company);
    }
}
