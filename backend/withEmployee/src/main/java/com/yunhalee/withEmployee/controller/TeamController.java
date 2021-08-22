package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.TeamDTO;
import com.yunhalee.withEmployee.dto.TeamListDTO;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamService service;

    @GetMapping("/team/teamlist")
    public List<TeamDTO> listAll(){
        List<Team> teams = service.listAll();
        List<TeamDTO> teamDTOS = new ArrayList<TeamDTO>();

        teams.forEach(team->{
            teamDTOS.add(new TeamDTO(team));
        });

        return teamDTOS;
    }

    @GetMapping("/team/{id}")
    public TeamDTO getById(@PathVariable("id") Integer id){
//        List <Team> teams = service.getByUserId(id);
//        List<TeamDTO> teamDTOS = new ArrayList<TeamDTO>();
//
//        teams.forEach(team->{
//            teamDTOS.add(new TeamDTO(team));
//        });

        Team team = service.getById(id);
        TeamDTO teamDTO = new TeamDTO((team));

        return teamDTO;
    }

    @GetMapping("/teams/{id}")
    public List<TeamListDTO> getByUserId(@PathVariable("id") Integer id){
        List<TeamListDTO> teamListDTOS = new ArrayList<>();
        List<Team> teams= service.getByUserId(id);
        teams.forEach(team -> teamListDTOS.add(new TeamListDTO(team)));

        return teamListDTOS;
    }
}
