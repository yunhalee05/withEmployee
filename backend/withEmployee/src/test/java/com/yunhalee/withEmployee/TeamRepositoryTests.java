package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.TeamRepository;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TeamRepositoryTests {

    @Autowired
    private TeamRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateTeam(){
        Team team = new Team("team 7");
        Team savedteam = repo.save(team);

        assertThat(savedteam.getId()).isNotNull();
    }

    @Test
    public void testListAllByMember(){
        User member = entityManager.find(User.class,5);
        List<Team> teams= repo.findByUsers(member);

       teams.forEach(t-> System.out.println(t));

        assertThat(teams.size()).isNotNull();
    }

    @Test
    public void testUpdateTeam(){
        Team team = repo.findById(4).get();
        Company company = entityManager.find(Company.class,9);

        team.setCompany(company);
        Team savedteam = repo.save(team);

        System.out.println(savedteam);
    }

    @Test
    public void testListAllByCompany(){
        Company company = entityManager.find(Company.class,3);

        List<Team> teams = repo.findByCompany(company);
        teams.forEach(t-> System.out.println(t));
        assertThat(teams.size()).isNotNull();
    }

    @Test
    public void testListAllTeams(){
        Iterable<Team> teams = repo.findAll();

        for (Team team : teams) {
            System.out.println(team);
        }

    }
}
