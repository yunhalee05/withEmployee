package com.yunhalee.withEmployee.team.service;

import com.yunhalee.withEmployee.company.domain.CompanyRepository;
import com.yunhalee.withEmployee.company.dto.SimpleCompanyResponse;
import com.yunhalee.withEmployee.team.domain.TeamRepository;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponse;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponses;
import com.yunhalee.withEmployee.team.dto.TeamResponse;
import com.yunhalee.withEmployee.team.dto.TeamResponses;
import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.team.dto.TeamDTO;
import com.yunhalee.withEmployee.team.dto.TeamListByPageDTO;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import com.yunhalee.withEmployee.user.service.UserService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    public static final int TEAM_PER_PAGE = 9;

    @Autowired
    private TeamRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private UserService userService;

    public TeamResponses listAll(Integer page){
        Pageable pageable = PageRequest.of(page-1,TEAM_PER_PAGE, Sort.by("id"));
        Page<Team> pageTeam = repo.findAllTeams(pageable);
        return TeamResponses.of(pageTeam.getTotalElements(),
            pageTeam.getTotalPages(),
            pageTeam.getContent().stream()
                .map(team -> TeamResponse.of(team, userService.simpleUserResponses(team.getUsers())))
                .collect(Collectors.toList()));

    }

    public SimpleTeamResponses getByUserId(Integer userId){
        return SimpleTeamResponses.of(
            repo.findByUserId(userId).stream()
            .map(team -> SimpleTeamResponse.of(team, SimpleCompanyResponse.of(team.getCompany())))
            .collect(Collectors.toList()));
    }

    public TeamResponse getById(Integer id){
        Team team = repo.findByTeamId(id);
        return TeamResponse.of(team, userService.simpleUserResponses(team.getUsers()));
    }

    public TeamDTO save(TeamDTO teamDTO){
        if(teamDTO.getId()!=null){
            Team existingTeam = repo.findById(teamDTO.getId()).get();
            existingTeam.setName(teamDTO.getName());
            repo.save(existingTeam);
            return new TeamDTO(existingTeam);
        }
        Team team = new Team(teamDTO.getName());
        Company company = companyRepo.findById(teamDTO.getCompanyId()).get();
        team.setCompany(company);
        repo.save(team);

        return new TeamDTO(team);
    }

    public void delete(Integer id){
        repo.deleteById(id);
    }

    public boolean isNameUnique(String name, Integer id){
        List<Team> teams = repo.findByCompanyId(id);

        for (Team team : teams) {
            if (team.getName() == name) return false;
        }

        return true;
    }
}
