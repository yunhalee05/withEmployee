package com.yunhalee.withEmployee.user.domain;

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

}
