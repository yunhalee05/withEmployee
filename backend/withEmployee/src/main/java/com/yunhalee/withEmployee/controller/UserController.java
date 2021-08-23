package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.UserDTO;
import com.yunhalee.withEmployee.entity.User;
import com.yunhalee.withEmployee.security.JwtUserDetails;
import com.yunhalee.withEmployee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            userDTO.setPassword("");
            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        UserDTO savedUserDTO = service.save(userDTO);
        userDTO.setPassword("");
        return new ResponseEntity<UserDTO>(savedUserDTO, HttpStatus.CREATED);
    }


    @PostMapping("/user/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
//        System.out.println(userDTO);
        UserDTO savedUserDTO = service.save(userDTO);

        savedUserDTO.setPassword("");
        return new ResponseEntity<UserDTO>(savedUserDTO, HttpStatus.OK);
    }


    @PostMapping("/user/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email){
        return service.isEmailUnique(id, email)? "OK" : "Duplicated";
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserDTO> login(@RequestBody Map<String, String> body) throws IllegalAccessException {
        String email = body.get("username");
        String password = body.get("password");
        System.out.println(email);
        System.out.println(password);

        UserDTO user = service.getByEmail(email);

        if(!passwordEncoder.matches( password ,user.getPassword())){
            throw new IllegalAccessException("Wrong Password");
        }

        user.setPassword("");
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }


}
