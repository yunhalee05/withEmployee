package com.yunhalee.withEmployee.team.service;

import com.yunhalee.withEmployee.common.exception.exceptions.AuthException;
import com.yunhalee.withEmployee.company.dto.SimpleCompanyResponse;
import com.yunhalee.withEmployee.company.service.CompanyService;
import com.yunhalee.withEmployee.security.jwt.LoginUser;
import com.yunhalee.withEmployee.team.domain.TeamRepository;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponse;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponses;
import com.yunhalee.withEmployee.team.dto.TeamRequest;
import com.yunhalee.withEmployee.team.dto.TeamResponse;
import com.yunhalee.withEmployee.team.dto.TeamResponses;
import com.yunhalee.withEmployee.team.exception.TeamNameAlreadyInUseException;
import com.yunhalee.withEmployee.team.exception.TeamNameEmptyException;
import com.yunhalee.withEmployee.team.exception.TeamNotFoundException;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import com.yunhalee.withEmployee.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TeamService {

    public static final int TEAM_PER_PAGE = 9;

    private TeamRepository teamRepository;
    private CompanyService companyService;
    private UserService userService;

    public TeamService(TeamRepository teamRepository, CompanyService companyService,
        UserService userService) {
        this.teamRepository = teamRepository;
        this.companyService = companyService;
        this.userService = userService;
    }

    public TeamResponses listAll(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, TEAM_PER_PAGE, Sort.by("id"));
        Page<Team> pageTeam = teamRepository.findAllTeams(pageable);
        return teamResponses(pageTeam);
    }

    public SimpleTeamResponses getByUserId(Integer userId) {
        return SimpleTeamResponses.of(teamRepository.findByUserId(userId).stream()
            .map(team -> SimpleTeamResponse.of(team, SimpleCompanyResponse.of(team.getCompany())))
            .collect(Collectors.toList()));
    }

    public TeamResponse getById(LoginUser loginUser, Integer id) {
        Team team = findById(id);
        checkIsMember(loginUser, team);
        return TeamResponse.of(team, userService.simpleUserResponses(team.getUsers()));
    }

    public void checkIsMember(LoginUser loginUser, Team team) {
        if (!(team.isMember(loginUser.getId()) || team.isCeo(loginUser.getId()))) {
            throw new AuthException("User don't have authorization.");
        }
    }

    public void checkIsCeo(LoginUser loginUser, Team team) {
        if (!team.isCeo(loginUser.getId())) {
            throw new AuthException("User don't have authorization.");
        }
    }


    @Transactional
    public TeamResponse create(TeamRequest request) {
        checkName(request.getName());
        Company company = companyService.findCompanyById(request.getCompanyId());
        Team team = teamRepository.save(request.toTeam(company));
        return TeamResponse.of(team, userService.simpleUserResponses(team.getUsers()));
    }

    private void checkName(String name) {
        checkNameIsEmpty(name);
        if (teamRepository.existsByName(name)) {
            throw new TeamNameAlreadyInUseException("This team name is already in use. name : " + name);
        }
    }

    private void checkNameIsEmpty(String name) {
        if (name.isBlank() || name.isEmpty()) {
            throw new TeamNameEmptyException("Name could not be empty.");
        }
    }

    @Transactional
    public TeamResponse update(LoginUser loginUser, Integer id, TeamRequest request) {
        checkName(id, request.getName());
        Team team = findTeamById(id);
        checkIsCeo(loginUser, team);
        team.changeName(request.getName());
        return TeamResponse.of(team, userService.simpleUserResponses(team.getUsers()));
    }

    private void checkName(Integer id, String name) {
        checkNameIsEmpty(name);
        if (teamRepository.existsByName(name) && !findTeamByName(name).isId(id)) {
            throw new TeamNameAlreadyInUseException("This team name is already in use. name : " + name);
        }
    }

    @Transactional
    public void delete(LoginUser loginUser, Integer id) {
        Team team = findTeamById(id);
        checkIsCeo(loginUser, team);
        teamRepository.delete(team);
    }


    private Team findById(Integer id) {
        return teamRepository.findByTeamId(id)
            .orElseThrow(() -> new TeamNotFoundException("Team does not exist with id : " + id));
    }

    public Team findTeamById(Integer id) {
        return teamRepository.findById(id)
            .orElseThrow(() -> new TeamNotFoundException("Team does not exist with id : " + id));
    }

    private Team findTeamByName(String name) {
        return teamRepository.findByName(name)
            .orElseThrow(() -> new TeamNotFoundException("Team does not exist with name : " + name));
    }

    @Transactional
    public SimpleUserResponse addMember(LoginUser loginUser, Integer id, String email) {
        Team team = findTeamById(id);
        checkIsLeader(loginUser, team);
        User user = userService.findUserByEmail(email);
        team.addMember(user);
        return SimpleUserResponse.of(user);
    }

    @Transactional
    public void subtractMember(LoginUser loginUser, Integer id, Integer userId) {
        Team team = findTeamById(id);
        checkIsLeader(loginUser, team);
        User user = userService.findUserById(userId);
        team.subtractMember(user);
    }


    private void checkIsLeader(LoginUser loginUser, Team team) {
        if (!(team.isMember(loginUser.getId()) && (loginUser.isLeaderLevel())) && (!loginUser
            .isAdmin()) && (!loginUser.isCeo())) {
            throw new AuthException("User don't have authorization.");
        }
    }

    private TeamResponses teamResponses(Page<Team> pageTeam) {
        return TeamResponses.of(pageTeam.getTotalElements(),
            pageTeam.getTotalPages(),
            teamResponses(pageTeam.getContent()));
    }

    private List<TeamResponse> teamResponses(List<Team> teams) {
        return teams.stream()
            .map(team -> TeamResponse.of(team, userService.simpleUserResponses(team.getUsers())))
            .collect(Collectors.toList());
    }
}
