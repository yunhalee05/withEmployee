package com.yunhalee.withEmployee.company.dto;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.dto.CompanyDTO.CompanyCeo;
import com.yunhalee.withEmployee.company.dto.CompanyDTO.CompanyTeam;
import com.yunhalee.withEmployee.company.dto.CompanyDTO.CompanyUser;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyResponse {

    private Integer id;
    private String name;
    private String description;
    private List<CompanyTeamResponse> teams;
    private CompanyCeoResponse ceo;
    private List<CompanyUserResponse> members;

    private CompanyResponse(Company company, List<CompanyTeamResponse> teams, CompanyCeoResponse ceo, List<CompanyUserResponse> members) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.teams = teams;
        this.ceo = ceo;
        this.members = members;
    }

    public static CompanyResponse of(Company company, List<CompanyTeamResponse> teams, CompanyCeoResponse ceo, List<CompanyUserResponse> members){
        return new CompanyResponse(company, teams, ceo, members);
    }

    public static CompanyResponse of(Company company, CompanyCeoResponse ceo){
        return new CompanyResponse(company, new ArrayList<>(), ceo, new ArrayList<>());
    }


}
