package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.TeamRepository;
import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository repo;

    @Autowired
    private UserRepository userRepo;

    public List<Team> listAll(){
        return repo.findAllTeams();
    }

    public List<Team> getByUserId(Integer id){
        User user = userRepo.findById(id).get();

        return repo.findByUsers(user);
    }
}
