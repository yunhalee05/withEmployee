package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.UserDTO;
import com.yunhalee.withEmployee.entity.User;
import com.yunhalee.withEmployee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/user/userlist")
    public List<UserDTO> listAll(){
        List<User> users = service.listAll();

        ArrayList<UserDTO> userDTOS = new ArrayList<UserDTO>();

        users.forEach(user->
                userDTOS.add(new UserDTO(user))
        );

        return userDTOS;
    }
}
