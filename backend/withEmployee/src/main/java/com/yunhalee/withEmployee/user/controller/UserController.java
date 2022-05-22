package com.yunhalee.withEmployee.user.controller;

import com.yunhalee.withEmployee.security.jwt.UserTokenResponse;
import com.yunhalee.withEmployee.user.dto.UserRequest;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import com.yunhalee.withEmployee.user.dto.UserResponses;
import com.yunhalee.withEmployee.user.service.UserService;
import java.net.URI;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponses> getAll(@Param("page") Integer page) {
        return ResponseEntity.ok(userService.getAll(page));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PostMapping(value = "/users", consumes = {"multipart/form-data"})
    public ResponseEntity register(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestPart("userRequest") UserRequest userRequest) {
        Integer id = userService.register(userRequest, multipartFile);
        return ResponseEntity.created(URI.create("/users/" + id)).build();
    }

    @PostMapping(value = "/users/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<UserTokenResponse> update(@PathVariable("id") Integer id, @RequestPart("userRequest") UserRequest userRequest, @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok(userService.update(id, userRequest, multipartFile));
    }


}
