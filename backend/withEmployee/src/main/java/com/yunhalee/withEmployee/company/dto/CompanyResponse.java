package com.yunhalee.withEmployee.company.dto;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponse;
import com.yunhalee.withEmployee.user.dto.CeoResponse;
import com.yunhalee.withEmployee.user.dto.MemberResponse;
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
    private List<SimpleTeamResponse> teams;
    private CeoResponse ceo;
    private List<MemberResponse> members;

    private CompanyResponse(Company company, List<SimpleTeamResponse> teams, CeoResponse ceo, List<MemberResponse> members) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.teams = teams;
        this.ceo = ceo;
        this.members = members;
    }

    public static CompanyResponse of(Company company, List<SimpleTeamResponse> teams, CeoResponse ceo, List<MemberResponse> members) {
        return new CompanyResponse(company, teams, ceo, members);
    }

    public static CompanyResponse of(Company company, CeoResponse ceo) {
        return new CompanyResponse(company, new ArrayList<>(), ceo, new ArrayList<>());
    }


}
