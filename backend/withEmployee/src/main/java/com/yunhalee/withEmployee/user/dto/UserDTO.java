package com.yunhalee.withEmployee.user.dto;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.user.domain.User;
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

    private List<UserCompany> companies;

    private String role;

    public UserDTO(){
    }


    public UserDTO(Integer id, String name, String email, String password, String description, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    public UserDTO(String name, String email, String password, String description, String phoneNumber, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.name=user.getName();
        this.email = user.getEmail();
        this.password= user.getPassword();
        this.description = user.getDescription();
        this.imageUrl = user.getImageUrl();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole().getName();
        this.teams = UserTeam.TeamList(user.getTeams());
        this.companies = UserCompany.CompanyList(user.getCompanies());
    }


    @Getter
    static class UserTeam{
        private Integer id;

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
            this.id = team.getId();
            this.name = team.getName();
            if(team.getCompany()!=null){
                this.company = team.getCompany().getName();
            }
        }

    }


    @Getter
    static class UserCompany{
        private Integer id;

        private String name;

        static List<UserCompany> CompanyList(Set<Company> companies){
            List<UserCompany> list = new ArrayList<>();
            companies.forEach(company->{
                list.add(new UserCompany(company));
            });

            return list;
        }

        public UserCompany(Company company){
            this.id = company.getId();
            this.name = company.getName();
        }

    }
}