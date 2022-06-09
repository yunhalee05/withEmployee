package com.yunhalee.withEmployee.user.domain;

import com.yunhalee.withEmployee.RepositoryTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends RepositoryTest {

    public static final int USER_PER_PAGE = 3;

    private User member;
    private User ceo;
    private User leader;
    private User admin;

    @Before
    public void setUp() {
        member = save(UserTest.MEMBER);
        ceo = save(UserTest.CEO);
        leader = save(UserTest.LEADER);
        admin = save(UserTest.ADMIN);
    }

    @Test
    public void find_users() {
        Pageable pageable = PageRequest.of(0, USER_PER_PAGE);
        Page<User> page = userRepository.findAllUsers(pageable);
        assertThat(page.getContent().size()).isEqualTo(3);
    }


    public User save(User user) {
        return userRepository.save(User.builder()
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .imageUrl(user.getImageUrl())
            .description(user.getDescription())
            .phoneNumber(user.getPhoneNumber())
            .role(Role.valueOf(user.getRole())).build());
    }


}
