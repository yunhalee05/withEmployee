package com.yunhalee.withEmployee.user.dto;

import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserListByPageDTO {

    private Long totalElement;

    private Integer totalPage;

    private List<UserDTO> users;

    public UserListByPageDTO(Long totalElement, Integer totalPage, List<User> users) {
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.users = UserDTO.UserList(users);
    }


    @Getter
    static class UserDTO {

        private Integer id;

        private String name;

        private String email;

        private String description;

        private String phoneNumber;

        private List<String> teams;

        private List<String> companies;

        private String role;

        static List<UserDTO> UserList(List<User> users) {
            List<UserDTO> userDTOS = new ArrayList<>();
            users.forEach(user -> userDTOS.add(new UserDTO(user)));
            return userDTOS;
        }

        public UserDTO(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.description = user.getDescription();
            this.phoneNumber = user.getPhoneNumber();
            this.teams = user.getTeamNames();
            this.companies = user.getCompanyNames();
            this.role = user.getRole();
        }
    }
}
