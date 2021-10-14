package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.CompanyRepository;
import com.yunhalee.withEmployee.Repository.TeamRepository;
import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.dto.TeamDTO;
import com.yunhalee.withEmployee.dto.TeamListByPageDTO;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Team;
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

    public TeamListByPageDTO listAll(Integer page){
        Pageable pageable = PageRequest.of(page-1,TEAM_PER_PAGE, Sort.by("id"));
        Page<Team> pageTeam = repo.findAllTeams(pageable);
        List<Team> teams = pageTeam.getContent();

        TeamListByPageDTO teamListByPageDTO = new TeamListByPageDTO(pageTeam.getTotalElements(), pageTeam.getTotalPages(), teams);
        return teamListByPageDTO;

    }

    public List<Team> getByUserId(Integer id){
        return repo.findByUserId(id);
    }

    public Team getById(Integer id){
        return repo.findByTeamId(id);
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
