package com.yunhalee.withEmployee.user.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations = "/config/application-test.properties")
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    public static final int USER_PER_PAGE = 3;

    @Autowired
    private UserRepository userRepository;

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
