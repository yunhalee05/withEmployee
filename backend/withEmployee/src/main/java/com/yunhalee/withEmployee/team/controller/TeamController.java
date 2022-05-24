package com.yunhalee.withEmployee.team.controller;

import com.yunhalee.withEmployee.security.AuthenticationPrincipal;
import com.yunhalee.withEmployee.security.jwt.LoginUser;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponses;
import com.yunhalee.withEmployee.team.dto.TeamRequest;
import com.yunhalee.withEmployee.team.dto.TeamResponse;
import com.yunhalee.withEmployee.team.dto.TeamResponses;
import com.yunhalee.withEmployee.team.service.TeamService;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public ResponseEntity<TeamResponses> listAll(@Param("page") String page) {
        Integer pageTeam = Integer.parseInt(page);
        return ResponseEntity.ok(teamService.listAll(pageTeam));
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamResponse> getById(@AuthenticationPrincipal LoginUser loginUser, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(teamService.getById(loginUser, id));
    }

    @GetMapping("/users/{userId}/teams")
    public ResponseEntity<SimpleTeamResponses> getByUserId(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(teamService.getByUserId(userId));
    }

    @PostMapping("/teams")
    public ResponseEntity<TeamResponse> create(@AuthenticationPrincipal(isCeo = true) LoginUser loginUser, @RequestBody TeamRequest request) {
        return ResponseEntity.ok(teamService.create(request));
    }

    @PostMapping("/teams/{id}")
    public ResponseEntity<TeamResponse> update(@AuthenticationPrincipal(isCeo = true) LoginUser loginUser, @PathVariable("id") Integer id, @RequestBody TeamRequest request) {
        return ResponseEntity.ok(teamService.update(loginUser, id, request));
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal(isCeo = true) LoginUser loginUser, @PathVariable("id") Integer id) {
        teamService.delete(loginUser, id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "/teams/{id}", params = "email")
    public ResponseEntity<SimpleUserResponse> addMember(@AuthenticationPrincipal(isLeader = true) LoginUser loginUser, @PathVariable("id") Integer id, @RequestParam("email") String email) {
        return ResponseEntity.ok(teamService.addMember(loginUser, id, email));
    }

    @PostMapping(value = "/teams/{id}", params = "userId")
    public ResponseEntity subtractMember(@AuthenticationPrincipal(isLeader = true) LoginUser loginUser, @PathVariable("id") Integer id, @RequestParam("userId") Integer userId) {
        teamService.subtractMember(loginUser, id, userId);
        return ResponseEntity.noContent().build();
    }


}
