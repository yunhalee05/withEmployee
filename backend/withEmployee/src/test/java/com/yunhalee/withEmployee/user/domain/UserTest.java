package com.yunhalee.withEmployee.user.domain;

import com.yunhalee.withEmployee.company.domain.CompanyTest;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.team.domain.TeamTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UserTest {

    public static final User MEMBER = User.builder()
        .id(1)
        .name("testMember")
        .email("testMember@example.com")
        .password("123456")
        .imageUrl("testUploadFolder/testMemberImage.png")
        .description("This is testMember")
        .phoneNumber("01000000000")
        .role(Role.MEMBER).build();

    public static final User CEO = User.builder()
        .id(2)
        .name("testCEO")
        .email("testCEO@example.com")
        .password("123456")
        .imageUrl("testUploadFolder/testCEOImage.png")
        .description("This is testCEO")
        .phoneNumber("01000000000")
        .role(Role.CEO).build();

    public static final User LEADER = User.builder()
        .id(3)
        .name("testLeader")
        .email("testLeader@example.com")
        .password("123456")
        .imageUrl("testUploadFolder/testLeaderImage.png")
        .description("This is testLeader")
        .phoneNumber("01000000000")
        .role(Role.LEADER).build();

    public static final User ADMIN = User.builder()
        .id(4)
        .name("testAdmin")
        .email("testAdmin@example.com")
        .password("123456")
        .imageUrl("testUploadFolder/testAdminImage.png")
        .description("This is testAdmin")
        .phoneNumber("01000000000")
        .role(Role.ADMIN).build();

    public static final User SECOND_CEO = User.builder()
        .id(5)
        .name("testCEO2")
        .email("testCEO2@example.com")
        .password("123456")
        .imageUrl("testUploadFolder/testCEO2Image.png")
        .description("This is testCEO2")
        .phoneNumber("01000000001")
        .role(Role.CEO).build();


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
    public void addTeam() {
        user.addTeam(TeamTest.FIRST_TEAM);
        assertThat(user.getTeams().size()).isEqualTo(1);
    }

    @Test
    public void subtractTeam() {
        // given
        user.addTeam(TeamTest.FIRST_TEAM);
        user.addTeam(TeamTest.SECOND_TEAM);

        // when
        user.subtractTeam(TeamTest.FIRST_TEAM);

        // then
        assertThat(user.getTeams().size()).isEqualTo(1);
        assertThat(user.getTeams().contains(TeamTest.SECOND_TEAM)).isTrue();
        assertThat(user.getTeams().contains(TeamTest.FIRST_TEAM)).isFalse();
    }
}
