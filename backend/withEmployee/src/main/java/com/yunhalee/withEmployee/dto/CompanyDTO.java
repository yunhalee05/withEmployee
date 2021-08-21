package com.yunhalee.withEmployee.dto;

import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CompanyDTO {

    private Integer id;

    private String name;

    private String description;

    private List<CompanyTeam> teams;

    private Integer totalNumber;

    private List<String> users;


    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.teams = CompanyTeam.TeamList(company.getTeams());
        this.totalNumber = company.getTotalNumber();
        this.users = company.getUsers();
    }

    @Getter
    static class CompanyTeam{

        private String name;

        static List<CompanyDTO.CompanyTeam> TeamList(Set<Team> teams){
            List<CompanyDTO.CompanyTeam> list = new ArrayList<>();
            teams.forEach(team->{
                list.add(new CompanyTeam(team));
            });

            return list;
        }

        public CompanyTeam(Team team){
            this.name = team.getName();

        }

    }
}
