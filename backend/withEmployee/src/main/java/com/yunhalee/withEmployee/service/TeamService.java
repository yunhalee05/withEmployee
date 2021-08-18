package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.TeamRepository;
import com.yunhalee.withEmployee.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository repo;

    public List<Team> listAll(){
        return repo.findAllTeams();
    }
}
