package com.yunhalee.withEmployee.team.controller;

import com.yunhalee.withEmployee.team.dto.SimpleTeamResponses;
import com.yunhalee.withEmployee.team.dto.TeamDTO;
import com.yunhalee.withEmployee.team.dto.TeamListByPageDTO;
import com.yunhalee.withEmployee.team.dto.TeamListDTO;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.team.dto.TeamResponses;
import com.yunhalee.withEmployee.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamService service;

    @GetMapping("/teams")
    public ResponseEntity<TeamResponses> listAll(@Param("page")String page){
        Integer pageTeam = Integer.parseInt(page);
        return ResponseEntity.ok(service.listAll(pageTeam));
    }

    @GetMapping("/teams/{id}")
    public TeamDTO getById(@PathVariable("id") Integer id){
        Team team = service.getById(id);
        TeamDTO teamDTO = new TeamDTO((team));

        return teamDTO;
    }

    @GetMapping("/users/{userId}/teams")
    public ResponseEntity<SimpleTeamResponses> getByUserId(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @PostMapping("/teams")
    public TeamDTO save(@RequestBody TeamDTO teamDTO){
        return service.save(teamDTO);
    }

    @DeleteMapping("/teams/{id}")
    public void delete(@PathVariable("id") Integer id){
        service.delete(id);
    }


    @GetMapping("/team/check_name")
    public String check_name(@Param("name")String name, @Param("id")Integer id){
        return service.isNameUnique(name, id) ? "OK" : "Duplicated";
    }




}
