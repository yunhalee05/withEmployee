package com.yunhalee.withEmployee.user.controller;

import com.yunhalee.withEmployee.user.dto.UserDTO;
import com.yunhalee.withEmployee.user.dto.UserListByPageDTO;
import com.yunhalee.withEmployee.user.dto.UserRequest;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import com.yunhalee.withEmployee.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user/userlist")
    public UserListByPageDTO listAll(@Param("page") Integer page){

        return service.listAll(page);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> detailUser(@PathVariable("id") Integer id){
            UserDTO userDTO = service.get(id);
            userDTO.setPassword("");
            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
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
    public ResponseEntity<UserResponse> register(@RequestPart("multipartFile")MultipartFile multipartFile, @RequestPart("userRequest") UserRequest userRequest) throws IOException {
        return ResponseEntity.ok(service.register(userRequest, multipartFile));
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

    @PostMapping("/user/login")
    public ResponseEntity<UserDTO> login(@RequestBody Map<String, String> body) throws IllegalAccessException {
        String email = body.get("username");
        String password = body.get("password");

        UserDTO user = service.getByEmail(email);

        if(!passwordEncoder.matches( password ,user.getPassword())){
            throw new IllegalAccessException("Wrong Password");
        }

        user.setPassword("");
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }


}
