package com.yunhalee.withEmployee.team.domain;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.domain.CompanyTest;
import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamTest {

    public static final Team FIRST_TEAM = create(1, "testFirstTeam", CompanyTest.FIRST_COMPANY);
    public static final Team SECOND_TEAM = create(2, "testSecondTeam", CompanyTest.FIRST_COMPANY);
    public static final Team THIRD_TEAM = create(3, "testThirdTeam", CompanyTest.FIRST_COMPANY);

    private Team team = create(1, "testTeam", CompanyTest.FIRST_COMPANY);
    private User user = User.builder()
        .id(1)
        .name("testMember")
        .email("testMember@example.com")
        .password("123456")
        .imageUrl("testUploadFolder/testMemberImage.png")
        .description("This is testMember")
        .phoneNumber("01000000000")
        .role(Role.MEMBER).build();

    @Test
    public void addMember() {
        // when
        team.addMember(user);

        // then
        assertThat(team.getUsers().size()).isEqualTo(1);
        assertThat(user.getTeams().size()).isEqualTo(1);
    }

    @Test
    public void subtractMember() {
        // given
        User addUser = User.builder()
            .id(2)
            .name("testSubtract")
            .email("testSubtract@example.com")
            .password("123456")
            .imageUrl("testUploadFolder/testSubtract.png")
            .description("This is testSubtract")
            .phoneNumber("01000000001")
            .role(Role.LEADER).build();
        team.addMember(user);
        team.addMember(addUser);

        // when
        team.subtractMember(user);

        // then
        assertThat(team.getUsers().size()).isEqualTo(1);
        assertThat(team.getUsers().contains(addUser)).isTrue();
        assertThat(user.getTeams().size()).isEqualTo(0);
        assertThat(user.getTeams().contains(team)).isFalse();
        assertThat(addUser.getTeams().contains(team)).isTrue();
    }

    private static Team create(Integer id, String name, Company company) {
        return Team.builder()
            .id(id)
            .name(name)
            .company(company).build();
    }


}
