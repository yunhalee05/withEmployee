//package com.yunhalee.withEmployee.team.dto;
//
//import com.yunhalee.withEmployee.team.domain.Team;
//import com.yunhalee.withEmployee.user.domain.User;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//@Getter
//@Setter
//public class TeamDTO {
//
//    private Integer id;
//
//    private String name;
//
//    private String company;
//
//    private List<TeamUser> users;
//
//    private Integer companyId;
//
//    public TeamDTO() {
//    }
//
//    public TeamDTO(String name, Integer companyId) {
//        this.name = name;
//        this.companyId = companyId;
//    }
//
//    public TeamDTO(Integer id, String name, Integer companyId) {
//        this.id = id;
//        this.name = name;
//        this.companyId = companyId;
//    }
//
//    public TeamDTO(Team team){
//        this.id = team.getId();
//        this.name=team.getName();
//        this.company = team.getCompany().getName();
//        this.users = TeamDTO.TeamUser.UserList(team.getUsers());
//        this.companyId = team.getCompany().getId();
//
//    }
//
//
//    @Getter
//    static class TeamUser{
//        private Integer id;
//        private String name;
//        private String phoneNumber;
//        private String role;
//        private String email;
//        private String imageUrl;
//
//        static List<TeamUser> UserList(Set<User> users){
//            List<TeamDTO.TeamUser> list = new ArrayList<>();
//            users.forEach(user->{
//                list.add(new TeamDTO.TeamUser(user));
//            });
//
//            return list;
//        }
//
//        public TeamUser(User user){
//            this.name = user.getName();
//            this.id = user.getId();
//            this.phoneNumber = user.getPhoneNumber();
//            this.email = user.getEmail();
//            this.role = user.getRole();
//            this.imageUrl = user.getImageUrl();
//        }
//
//    }
//
//}
