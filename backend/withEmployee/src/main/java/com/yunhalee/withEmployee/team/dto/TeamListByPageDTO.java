//package com.yunhalee.withEmployee.team.dto;
//
//import com.yunhalee.withEmployee.team.domain.Team;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//public class TeamListByPageDTO {
//
//
//    private Long totalElement;
//
//    private Integer totalPage;
//
//    private List<TeamDTO> teams;
//
//    public TeamListByPageDTO(Long totalElement, Integer totalPage, List<Team> teams) {
//        this.totalElement = totalElement;
//        this.totalPage = totalPage;
//        this.teams =TeamDTO.TeamList(teams);
//    }
//
//
//    @Getter
//    static class TeamDTO{
//        private Integer id;
//
//        private String name;
//
//        private Integer totalNumber;
//
//        private String company;
//
//        static List<TeamDTO> TeamList(List<Team> teams) {
//            List<TeamDTO> teamDTOS = new ArrayList<>();
//            teams.forEach(team -> teamDTOS.add(new TeamDTO(team)));
//
//            return teamDTOS;
//        }
//
//
//
//        public TeamDTO(Team team){
//            this.id = team.getId();
//            this.name = team.getName();
//            this.totalNumber = team.getTotalNumber();
//            this.company = team.getCompany().getName();
//        }
//    }
//}
