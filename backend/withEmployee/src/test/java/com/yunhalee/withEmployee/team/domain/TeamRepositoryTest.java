package com.yunhalee.withEmployee.team.domain;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.domain.CompanyTest;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserTest;
import com.yunhalee.withEmployee.RepositoryTest;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TeamRepositoryTest extends RepositoryTest {

    public static final int TEAM_PER_PAGE = 3;

    private Team firstTeam;
    private Team secondTeam;
    private Team thirdTeam;
    private Company company;
    private User ceo;

    @Before
    public void setUp() {
        ceo = userRepository.save(UserTest.CEO);
        company = companyRepository.save(Company.builder()
            .name(CompanyTest.FIRST_COMPANY.getName())
            .description(CompanyTest.FIRST_COMPANY.getDescription())
            .ceo(ceo).build());
        firstTeam = save(TeamTest.FIRST_TEAM.getName(), company, ceo);
        secondTeam = save(TeamTest.SECOND_TEAM.getName(), company, ceo);
        thirdTeam = save(TeamTest.THIRD_TEAM.getName(), company, ceo);
    }

    @Test
    public void find_all_teams() {
        Pageable pageable = PageRequest.of(0, TEAM_PER_PAGE);
        Page<Team> page = teamRepository.findAllTeams(pageable);
        assertThat(page.getContent().size()).isEqualTo(3);
    }

    @Test
    public void find_team_by_id() {
        Team team = teamRepository.findByTeamId(firstTeam.getId()).get();
        assertThat(team).isEqualTo(firstTeam);
    }

    @Test
    public void find_teams_by_userId() {
        List<Team> teams = teamRepository.findByUserId(ceo.getId());
        assertThat(teams.equals(Arrays.asList(firstTeam, secondTeam, thirdTeam)));
    }

    private Team save(String name, Company company, User user) {
        Team team = Team.of(name, company);
        team.addMember(user);
        return teamRepository.save(team);
    }

    @Test
    public void create_team_with_already_existing_name_in_company_is_invalid() {
        assertThatThrownBy(() -> save(TeamTest.FIRST_TEAM.getName(), company, ceo))
            .isInstanceOf(DataIntegrityViolationException.class);
    }
}
