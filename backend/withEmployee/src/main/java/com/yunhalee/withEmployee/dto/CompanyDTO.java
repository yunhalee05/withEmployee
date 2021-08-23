package com.yunhalee.withEmployee.dto;

import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CompanyDTO {

    private Integer id;

    private String name;

    private String description;

    private List<CompanyTeam> teams;

    private CompanyCeo ceo;

    private List<CompanyUser> members;

    public CompanyDTO() {
    }

    public CompanyDTO(Integer id) {
        this.id = id;
    }

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.teams = CompanyTeam.TeamList(company.getTeams());
        this.ceo = new CompanyCeo(company.getCeo());
        this.members = CompanyUser.UserList(company.getTeams());

    }

    @Getter
    static class CompanyTeam{

        private Integer id;
        private String name;
        private Integer totalNumber;

        static List<CompanyDTO.CompanyTeam> TeamList(Set<Team> teams){
            List<CompanyDTO.CompanyTeam> list = new ArrayList<>();
            teams.forEach(team->{
                list.add(new CompanyTeam(team));
            });

            return list;
        }

        public CompanyTeam(Team team){
            this.id = team.getId();
            this.name = team.getName();
            this.totalNumber = team.getTotalNumber();

        }

    }

    @Getter
    static class CompanyCeo{
        private Integer id;
        private String name;
        private String email;

        public CompanyCeo(User ceo){
            this.id = ceo.getId();
            this.name = ceo.getName();
            this.email = ceo.getEmail();
        }
    }

    @Getter
    static class CompanyUser{
        private Integer id;
        private String role;
        private String name;
        private String email;

        static  List<CompanyDTO.CompanyUser> UserList(Set<Team> teams){
            List<CompanyDTO.CompanyUser> list = new ArrayList<>();
            Set<User> users = new HashSet<>();
            teams.forEach(team-> {
                users.addAll(team.getUsers());
            });
            users.forEach(user-> list.add(new CompanyUser(user)));
            return list;
        }

        public CompanyUser(User user){
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.role = user.getRole().getName();
        }
    }
}
