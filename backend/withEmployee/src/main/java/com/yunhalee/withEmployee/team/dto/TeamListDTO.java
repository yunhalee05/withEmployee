//package com.yunhalee.withEmployee.team.dto;
//
//import com.yunhalee.withEmployee.company.domain.Company;
//import com.yunhalee.withEmployee.team.domain.Team;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class TeamListDTO {
//
//    private Integer id;
//
//    private String name;
//
//    private Integer totalNumber;
//
//    private TeamCompany company;
//
//    public TeamListDTO(Team team){
//        this.id = team.getId();
//        this.name=team.getName();
//        this.company = new TeamCompany(team.getCompany());
//        this.totalNumber = team.getTotalNumber();
//    }
//
//
//    @Getter
//    static class TeamCompany{
//        private Integer id;
//        private String name;
//        private String ceo;
//
//        public TeamCompany(Company company){
//            this.name = company.getName();
//            this.id = company.getId();
//            this.ceo = company.getCeo().getName();
//        }
//    }
//
//
//
//}
