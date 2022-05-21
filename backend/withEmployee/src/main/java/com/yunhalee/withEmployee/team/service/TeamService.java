package com.yunhalee.withEmployee.team.service;

import com.yunhalee.withEmployee.company.dto.SimpleCompanyResponse;
import com.yunhalee.withEmployee.company.service.CompanyService;
import com.yunhalee.withEmployee.team.domain.TeamRepository;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponse;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponses;
import com.yunhalee.withEmployee.team.dto.TeamRequest;
import com.yunhalee.withEmployee.team.dto.TeamResponse;
import com.yunhalee.withEmployee.team.dto.TeamResponses;
import com.yunhalee.withEmployee.team.exception.TeamNameAlreadyExistException;
import com.yunhalee.withEmployee.team.exception.TeamNotFoundException;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.user.service.UserService;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TeamService {

    public static final int TEAM_PER_PAGE = 9;

    private TeamRepository teamRepository;
    private CompanyService companyService;
    private UserService userService;

    public TeamService(TeamRepository teamRepository, CompanyService companyService, UserService userService) {
        this.teamRepository = teamRepository;
        this.companyService = companyService;
        this.userService = userService;
    }

    public TeamResponses listAll(Integer page){
        Pageable pageable = PageRequest.of(page-1,TEAM_PER_PAGE, Sort.by("id"));
        Page<Team> pageTeam = teamRepository.findAllTeams(pageable);
        return TeamResponses.of(pageTeam.getTotalElements(),
            pageTeam.getTotalPages(),
            pageTeam.getContent().stream()
                .map(team -> TeamResponse.of(team, userService.simpleUserResponses(team.getUsers())))
                .collect(Collectors.toList()));

    }

    public SimpleTeamResponses getByUserId(Integer userId){
        return SimpleTeamResponses.of(
            teamRepository.findByUserId(userId).stream()
            .map(team -> SimpleTeamResponse.of(team, SimpleCompanyResponse.of(team.getCompany())))
            .collect(Collectors.toList()));
    }

    public TeamResponse getById(Integer id){
        Team team = teamRepository.findByTeamId(id);
        return TeamResponse.of(team, userService.simpleUserResponses(team.getUsers()));
    }

    @Transactional
    public TeamResponse create(TeamRequest request) {
        checkName(request.getName());
        Company company = companyService.findCompanyById(request.getCompanyId());
        Team team = teamRepository.save(request.toTeam(company));
        return TeamResponse.of(team, userService.simpleUserResponses(team.getUsers()));
    }

    private void checkName(String name) {
        if (teamRepository.existsByName(name)) {
            throw new TeamNameAlreadyExistException("Team already exists with name : " + name);
        }
    }

    @Transactional
    public TeamResponse update(Integer id, TeamRequest request) {
        checkName(id, request.getName());
        Team team = findTeamById(id);
        team.changeName(request.getName());
        return TeamResponse.of(team, userService.simpleUserResponses(team.getUsers()));
    }

    private void checkName(Integer id, String name){
        if (teamRepository.existsByName(name) && !findTeamByName(name).isId(id)) {
            throw new TeamNameAlreadyExistException("Team already exists with name : " + name);
        }
    }

    @Transactional
    public void delete(Integer id){
        teamRepository.deleteById(id);
    }

//    public boolean isNameUnique(String name, Integer id){
//        List<Team> teams = teamRepository.findByCompanyId(id);
//
//        for (Team team : teams) {
//            if (team.getName() == name) return false;
//        }
//
//        return true;
//    }

    public Team findTeamById(Integer id) {
        return teamRepository.findById(id)
            .orElseThrow(() ->new TeamNotFoundException("Team does not exist with id : " + id));
    }

    private Team findTeamByName(String name) {
        return teamRepository.findByName(name)
            .orElseThrow(() ->new TeamNotFoundException("Team does not exist with name : " + name));
    }
}
