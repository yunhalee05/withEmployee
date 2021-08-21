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
public class TeamDTO {

    private Integer id;

    private String name;

    private String company;

    private List<TeamUser> users;

    public TeamDTO(Team team){
        this.id = team.getId();
        this.name=team.getName();
        this.company = team.getCompany().getName();
        this.users = TeamDTO.TeamUser.UserList(team.getUsers());
    }


    @Getter
    static class TeamUser{
        private Integer id;
        private String name;
        private String phoneNumber;
        private String role;

        static List<TeamUser> UserList(Set<User> users){
            List<TeamDTO.TeamUser> list = new ArrayList<>();
            users.forEach(user->{
                list.add(new TeamDTO.TeamUser(user));
            });

            return list;
        }

        public TeamUser(User user){
            this.name = user.getName();
            this.id = user.getId();
            this.phoneNumber = user.getPhoneNumber();
            this.role = user.getRoleName();
        }

    }

}
