package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.UserDTO;
import com.yunhalee.withEmployee.entity.User;
import com.yunhalee.withEmployee.exception.UserNotFoundException;
import com.yunhalee.withEmployee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> detailUser(@PathVariable("id") Integer id){
            UserDTO userDTO = service.get(id);
            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        service.save(userDTO);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }


    @PostMapping("/user/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email){
        return service.isEmailUnique(id, email)? "OK" : "Duplicated";
    }

}
