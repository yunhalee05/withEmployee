package com.yunhalee.withEmployee.team.controller;

import com.yunhalee.withEmployee.team.dto.TeamDTO;
import com.yunhalee.withEmployee.team.dto.TeamListByPageDTO;
import com.yunhalee.withEmployee.team.dto.TeamListDTO;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamService service;

    @GetMapping("/teams")
    public TeamListByPageDTO listAll(@Param("page")String page){
        Integer pageTeam = Integer.parseInt(page);

        return service.listAll(pageTeam);
    }

    @GetMapping("/teams/{id}")
    public TeamDTO getById(@PathVariable("id") Integer id){
        Team team = service.getById(id);
        TeamDTO teamDTO = new TeamDTO((team));

        return teamDTO;
    }

    @GetMapping("/teams/{userId}/users")
    public List<TeamListDTO> getByUserId(@PathVariable("id") Integer id){
        List<TeamListDTO> teamListDTOS = new ArrayList<>();
        List<Team> teams= service.getByUserId(id);
        teams.forEach(team -> teamListDTOS.add(new TeamListDTO(team)));

        return teamListDTOS;
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
