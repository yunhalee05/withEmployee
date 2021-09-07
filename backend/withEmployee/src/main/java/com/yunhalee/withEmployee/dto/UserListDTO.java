package com.yunhalee.withEmployee.dto;

import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserListDTO {

    private Long totalElement;

    private Integer totalPage;

    private List<UserDTO> userDTOS;

    public UserListDTO(Long totalElement, Integer totalPage, List<UserDTO> userDTOS) {
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.userDTOS = userDTOS;
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


}
