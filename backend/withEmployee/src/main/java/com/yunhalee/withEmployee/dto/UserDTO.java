package com.yunhalee.withEmployee.dto;

import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Integer id;

    private String name;

    private String email;

    private String password;

    private String description;

    private String imageUrl;

    private String phoneNumber;

    private List<UserTeam> teams;

    private String role;

    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.name=user.getName();
        this.email = user.getEmail();
        this.description = user.getDescription();
        this.imageUrl = user.getImageUrl();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole().getName();
        this.teams = UserTeam.TeamList(user.getTeams());
    }

    @Getter
    static class UserTeam{
        private String name;

        private String company;

        static List<UserTeam> TeamList(Set<Team> teams){
            List<UserTeam> list = new ArrayList<>();
            teams.forEach(team->{
                list.add(new UserTeam(team));
            });

            return list;
        }

        public UserTeam(Team team){
            this.name = team.getName();
            if(team.getCompany()!=null){
                this.company = team.getCompany().getName();
            }
        }

    }
}