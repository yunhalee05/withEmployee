package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.CompanyRepository;
import com.yunhalee.withEmployee.Repository.TeamRepository;
import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.dto.TeamDTO;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CompanyRepository companyRepo;

    public List<Team> listAll(){
        return repo.findAllTeams();
    }

    public List<Team> getByUserId(Integer id){
        return repo.findByUserId(id);
    }

    public Team getById(Integer id){
        return repo.findByTeamId(id);
    }

    public TeamDTO save(TeamDTO teamDTO){
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
