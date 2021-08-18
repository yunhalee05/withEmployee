package com.yunhalee.withEmployee.dto;

import com.yunhalee.withEmployee.entity.Role;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Integer id;

    private String name;

    private String email;

    private String description;

    private String imageUrl;

    private String phoneNumber;

    private List<String> teams;

    private String role;

    public UserDTO(User user){
        this.id = user.getId();
        this.name=user.getName();
        this.email = user.getEmail();
        this.description = user.getDescription();
        this.imageUrl = user.getImageUrl();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole().getName();
    }
}