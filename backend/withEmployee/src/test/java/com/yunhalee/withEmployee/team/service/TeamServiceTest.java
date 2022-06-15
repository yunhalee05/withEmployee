package com.yunhalee.withEmployee.team.service;

import com.yunhalee.withEmployee.ServiceTest;
import com.yunhalee.withEmployee.security.jwt.LoginUser;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.team.domain.TeamTest;
import com.yunhalee.withEmployee.team.dto.TeamRequest;
import com.yunhalee.withEmployee.team.dto.TeamResponse;
import com.yunhalee.withEmployee.user.domain.UserTest;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class TeamServiceTest extends ServiceTest {

    @InjectMocks
    private TeamService teamService = new TeamService(teamRepository, companyService, userService);

    private Team team;
    private TeamRequest request;

    @BeforeEach
    void setUp() {
        team = Team.builder()
            .id(TeamTest.FIRST_TEAM.getId())
            .name(TeamTest.FIRST_TEAM.getName())
            .company(TeamTest.FIRST_TEAM.getCompany()).build();

        request = new TeamRequest(TeamTest.FIRST_TEAM.getName(), TeamTest.FIRST_TEAM.getCompanyId());
    }

    @Test
    void create_team() {
        // when
        when(companyService.findCompanyById(anyInt())).thenReturn(TeamTest.FIRST_TEAM.getCompany());
        when(teamRepository.save(any())).thenReturn(team);
        when(userService.simpleUserResponses(any())).thenReturn(new ArrayList<>());
        TeamResponse response = teamService.create(request);

        // then
        equals(response, team);
    }


    @Test
    void update_team() {
        // given
        TeamRequest teamRequest = new TeamRequest(TeamTest.SECOND_TEAM.getName(), TeamTest.FIRST_TEAM.getCompanyId());

        // when
        when(teamRepository.findById(any())).thenReturn(Optional.of(team));
        when(userService.simpleUserResponses(any())).thenReturn(new ArrayList<>());
        TeamResponse response = teamService.update(LoginUser.of(UserTest.CEO), team.getId(), teamRequest);

        // then
        assertThat(response.getName()).isEqualTo(TeamTest.SECOND_TEAM.getName());
    }


    private void equals(TeamResponse response, Team team) {
        assertThat(response.getId()).isEqualTo(team.getId());
        assertThat(response.getName()).isEqualTo(team.getName());
        assertThat(response.getCompanyId()).isEqualTo(team.getCompanyId());
        assertThat(response.getCompany()).isEqualTo(team.getCompany().getName());
        assertThat(response.getUsers().size()).isEqualTo(team.getUsers().size());
    }

}