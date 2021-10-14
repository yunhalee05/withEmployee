package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.TeamRepository;
import com.yunhalee.withEmployee.dto.TeamDTO;
import com.yunhalee.withEmployee.dto.TeamListByPageDTO;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamServiceTests {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void createTeam(){
        //given
        TeamDTO teamDTO = new TeamDTO("testTeam",1);

        //when
        TeamDTO teamDTO1 = teamService.save(teamDTO);

        //then
        assertEquals(teamDTO.getName(), teamDTO1.getName());
        assertEquals(teamDTO.getCompanyId(), teamDTO1.getCompanyId());
    }

    @Test
    public void updateTeam(){
        //given
        TeamDTO teamDTO = new TeamDTO("testTeam",1);
        TeamDTO teamDTO1 = teamService.save(teamDTO);

        teamDTO1.setName("testUpdateTeam");

        //when
        TeamDTO teamDTO2 = teamService.save(teamDTO1);

        //then
        assertNotEquals(teamDTO1.getName(), teamDTO2.getName());
    }

    @Test
    public void deleteTeam(){
        //given
        Integer id = 1;

        //when
        teamService.delete(id);

        //then
        assertNull(teamRepository.findById(id));
    }

    @Test
    public void isTeamNameUnique(){
        //given
        String name = "testTeam";
        Integer companyId = 1;

        //when
        boolean check = teamService.isNameUnique(name, companyId);

        //then
        assertEquals(check, false);
    }

    @Test
    public void getTeamById(){
        //given
        Integer id =1;

        //when
        Team team = teamService.getById(id);

        //then
        assertEquals(team.getId(), id);
    }

    @Test
    public void getTeamByUserId(){
        //given
        Integer userId =17;

        //when
        List<Team> teams = teamService.getByUserId(userId);

        //then
        List users = new ArrayList();
        teams.forEach(team -> {
            users.add(team.getUsers().stream().filter(user -> user.getId()==userId));
        });
        assertEquals(teams.size(), users.size());
    }

    @Test
    public void getTeamByPage(){
        //given
        Integer page = 1;

        //when
        TeamListByPageDTO teamListByPageDTO = teamService.listAll(page);

        //then
        assertEquals(9, teamListByPageDTO.getTeams().size());
    }
}
