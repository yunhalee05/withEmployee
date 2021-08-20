package com.yunhalee.withEmployee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MainController {

    @GetMapping("/home")
    public String viewHomePage(){
        return "Hello world";
    }

//    @GetMapping("/login")
//    public String loginPage(){
//        return "/login";
//    }
}
