package com.yunhalee.withEmployee.team.service;

import com.yunhalee.withEmployee.MockBeans;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.team.domain.TeamTest;
import com.yunhalee.withEmployee.team.dto.TeamRequest;
import com.yunhalee.withEmployee.team.dto.TeamResponse;
import com.yunhalee.withEmployee.team.exception.TeamNameAlreadyInUseException;
import com.yunhalee.withEmployee.team.exception.TeamNameEmptyException;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Transactional
class TeamServiceTest extends MockBeans {

    private static final String TEAM_NAME_IS_ALREADY_IN_USE_EXCEPTION = "This team name is already in use.";
    private static final String NAME_IS_EMPTY_EXCEPTION = "Name could not be empty.";

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

        request = new TeamRequest(TeamTest.FIRST_TEAM.getName(),
            TeamTest.FIRST_TEAM.getCompanyId());
    }

    @Test
    void create_team() {
        // when
        when(teamRepository.existsByName(anyString())).thenReturn(false);
        when(companyService.findCompanyById(anyInt())).thenReturn(TeamTest.FIRST_TEAM.getCompany());
        when(teamRepository.save(any())).thenReturn(team);
        when(userService.simpleUserResponses(any())).thenReturn(new ArrayList<>());
        TeamResponse response = teamService.create(request);

        // then
        equals(response, team);
    }

    @Test
    void create_team_with_empty_name_is_invalid() {
        TeamRequest teamRequest = new TeamRequest("", TeamTest.FIRST_TEAM.getCompanyId());
        when(teamRepository.existsByName(anyString())).thenReturn(false);
        assertThatThrownBy(() -> teamService.create(teamRequest))
            .isInstanceOf(TeamNameEmptyException.class)
            .hasMessage(NAME_IS_EMPTY_EXCEPTION);
    }

    @Test
    void create_team_with_already_existing_name_is_invalid() {
        when(teamRepository.existsByName(anyString())).thenReturn(true);
        assertThatThrownBy(() -> teamService.create(request))
            .isInstanceOf(TeamNameAlreadyInUseException.class)
            .hasMessageContaining(TEAM_NAME_IS_ALREADY_IN_USE_EXCEPTION);
    }

    @Test
    void update_team() {
        // given
        TeamRequest teamRequest = new TeamRequest(TeamTest.SECOND_TEAM.getName(), TeamTest.FIRST_TEAM.getCompanyId());

        // when
        when(teamRepository.existsByName(anyString())).thenReturn(false);
        when(teamRepository.findById(any())).thenReturn(Optional.of(team));
        when(userService.simpleUserResponses(any())).thenReturn(new ArrayList<>());
        TeamResponse response = teamService.update(team.getId(), teamRequest);

        // then
        assertThat(response.getName()).isEqualTo(TeamTest.SECOND_TEAM.getName());
    }

    @Test
    void update_team_with_already_existing_name_is_invalid() {
        // given
        TeamRequest teamRequest = new TeamRequest(TeamTest.SECOND_TEAM.getName(), TeamTest.FIRST_TEAM.getCompanyId());

        // when
        when(teamRepository.existsByName(anyString())).thenReturn(true);
        when(teamRepository.findByName(anyString())).thenReturn(Optional.of(TeamTest.SECOND_TEAM));
        assertThatThrownBy(() -> teamService.update(team.getId(), teamRequest))
            .isInstanceOf(TeamNameAlreadyInUseException.class)
            .hasMessageContaining(TEAM_NAME_IS_ALREADY_IN_USE_EXCEPTION);
    }

    private void equals(TeamResponse response, Team team) {
        assertThat(response.getId()).isEqualTo(team.getId());
        assertThat(response.getName()).isEqualTo(team.getName());
        assertThat(response.getCompanyId()).isEqualTo(team.getCompanyId());
        assertThat(response.getCompany()).isEqualTo(team.getCompany().getName());
        assertThat(response.getUsers().size()).isEqualTo(team.getUsers().size());
    }

}