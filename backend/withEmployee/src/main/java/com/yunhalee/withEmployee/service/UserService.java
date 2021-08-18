package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> listAll(){
       return repo.findAllUsers();
    }
}
