package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.TeamRepository;
import com.yunhalee.withEmployee.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

        //when

        //then
    }

    @Test
    public void updateTeam(){
        //given

        //when

        //then
    }

    @Test
    public void deleteTeam(){
        //given

        //when

        //then
    }

    @Test
    public void isTeamNameUnique(){
        //given

        //when

        //then
    }

    @Test
    public void getTeamById(){
        //given

        //when

        //then
    }
    @Test
    public void getTeamByUserId(){
        //given

        //when

        //then
    }
    @Test
    public void getTeamByPage(){
        //given

        //when

        //then
    }
}
