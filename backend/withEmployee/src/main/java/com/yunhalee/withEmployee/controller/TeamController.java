package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.TeamDTO;
import com.yunhalee.withEmployee.dto.TeamListByPageDTO;
import com.yunhalee.withEmployee.dto.TeamListDTO;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamService service;

    @GetMapping("/team/teamlist")
    public TeamListByPageDTO listAll(@Param("page")String page){
        Integer pageTeam = Integer.parseInt(page);

        return service.listAll(pageTeam);
    }

    @GetMapping("/team/{id}")
    public TeamDTO getById(@PathVariable("id") Integer id){
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

    @PostMapping("/team/save")
    public TeamDTO save(@RequestBody TeamDTO teamDTO){
        return service.save(teamDTO);
    }

    @DeleteMapping("/team/delete")
    public void delete(@Param("id") Integer id){
        service.delete(id);
    }


    @GetMapping("/team/check_name")
    public String check_name(@Param("name")String name, @Param("id")Integer id){
        return service.isNameUnique(name, id) ? "OK" : "Duplicated";
    }




}
