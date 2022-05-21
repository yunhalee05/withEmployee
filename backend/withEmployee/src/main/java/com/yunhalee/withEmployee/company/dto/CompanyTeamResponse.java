//package com.yunhalee.withEmployee.company.dto;
//
//import com.yunhalee.withEmployee.team.domain.Team;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//public class CompanyTeamResponse {
//
//    private Integer id;
//    private String name;
//    private Integer totalNumber;
//
//    private CompanyTeamResponse(Team team) {
//        this.id = team.getId();
//        this.name = team.getName();
//        this.totalNumber = team.getTotalNumber();
//    }
//
//    public static CompanyTeamResponse of(Team team) {
//        return new CompanyTeamResponse(team);
//    }
//}
