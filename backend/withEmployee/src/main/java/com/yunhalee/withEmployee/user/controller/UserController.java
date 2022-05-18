package com.yunhalee.withEmployee.user.controller;

import com.yunhalee.withEmployee.user.dto.UserDTO;
import com.yunhalee.withEmployee.user.dto.UserRequest;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import com.yunhalee.withEmployee.user.dto.UserResponses;
import com.yunhalee.withEmployee.user.service.UserService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<UserResponses> getAll(@Param("page") Integer page){
        return ResponseEntity.ok(service.getAll(page));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.get(id));
    }


    @PostMapping("/user/save")
    public ResponseEntity<UserDTO> saveUser(@RequestParam(value = "id",required = false)Integer id,
                                            @RequestParam("name")String name,
                                            @RequestParam("email")String email,
                                            @RequestParam(value = "password",required = false)String password,
                                            @RequestParam(value = "description",required = false)String description,
                                            @RequestParam(value = "phoneNumber", required = false)String phoneNumber,
                                            @RequestParam(value = "multipartFile", required = false)MultipartFile multipartFile) throws IOException {
        UserDTO userDTO = new UserDTO(id, name, email, password, description, phoneNumber);
//        UserDTO savedUserDTO = service.save(userDTO, multipartFile);
//        userDTO.setPassword("");
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }


//    @PostMapping("/user/register")
//    public ResponseEntity<UserDTO> register(@RequestParam("name")String name,
//                                            @RequestParam("email")String email,
//                                            @RequestParam("password")String password,
//                                            @RequestParam("description")String description,
//                                            @RequestParam("phoneNumber")String phoneNumber,
//                                            @RequestParam("role")String role,
//                                            @RequestParam("multipartFile")MultipartFile multipartFile) throws IOException {
//
//        UserDTO userDTO = new UserDTO(name, email, password, description, phoneNumber, role);
//        System.out.println(name + email + password +description + phoneNumber+ role);
//        UserDTO savedUserDTO = service.save(userDTO, multipartFile);
//
//        savedUserDTO.setPassword("");
//        return new ResponseEntity<UserDTO>(savedUserDTO, HttpStatus.OK);
//    }

    @PostMapping(value = "/users", consumes = { "multipart/form-data" })
    public ResponseEntity register(@RequestPart("multipartFile")MultipartFile multipartFile, @RequestPart("userRequest") UserRequest userRequest) throws IOException {
        Integer id = service.register(userRequest, multipartFile);
        return ResponseEntity.created(URI.create("/users/" + id)).build();
    }

    @PostMapping("/user/addTeam")
    public UserDTO addTeam(@Param("email")String email, @Param("id")Integer id){
        return service.addTeam(email, id);
    }

    @DeleteMapping("/user/deleteTeam")
    public Integer deleteTeam(@Param("userId")Integer userId, @Param("teamId")Integer teamId){
        return service.deleteTeam(userId, teamId);
    }


    @PostMapping("/user/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email){
        return service.isEmailUnique(id, email)? "OK" : "Duplicated";
    }


}
