package com.yunhalee.withEmployee.team.domain;

import com.yunhalee.withEmployee.team.domain.TeamRepository;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//public class TeamRepositoryTests {
//
//    @Autowired
//    private TeamRepository repo;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    public void testCreateTeam(){
//        Team team = new Team("team 7");
//        Team savedteam = repo.save(team);
//
//        assertThat(savedteam.getId()).isNotNull();
//    }
//
//    @Test
//    public void testListAllByMember(){
//        User member = entityManager.find(User.class,5);
//        List<Team> teams= repo.findByUserId(5);
//
//       teams.forEach(t-> System.out.println(t));
//
//        assertThat(teams.size()).isNotNull();
//    }
//
//    @Test
//    public void testUpdateTeam(){
//        Team team = repo.findByTeamId(7);
//        Company company = entityManager.find(Company.class,5);
//
//        team.setCompany(company);
//        Team savedteam = repo.save(team);
//
//        System.out.println(savedteam);
//    }
//
//    @Test
//    public void testListAllByCompany(){
//        Company company = entityManager.find(Company.class,3);
//
//        List<Team> teams = repo.findByCompany(company);
//        teams.forEach(t-> System.out.println(t));
//        assertThat(teams.size()).isNotNull();
//    }
//
//    @Test
//    public void testListAllTeams(){
//        Iterable<Team> teams = repo.findAll();
//
//        for (Team team : teams) {
//            System.out.println(team);
//        }
//
//    }
//
//    @Test
//    public void testListAllTeamsWithUser(){
//        List<Team> teams = repo.findAllTeams();
//        System.out.println(teams);
//    }
//
//    @Test
//    public void testGetTeamById(){
//        Team team = repo.findByTeamId(1);
//        System.out.println(team);
//    }
//
//    @Test
//    public void testGetTeamByCompanyId(){
//        List<Team> teams = repo.findByCompanyId(1);
//
//        for (Team team : teams) {
//            System.out.println(team);
//        }
//    }
//
//    @Test
//    public void testDeleteTeam(){
//        repo.deleteById(8);
//    }
//
//
//}
