package com.yunhalee.withEmployee.conversation.domain;

import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.user.domain.UserTest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConversationRepositoryTest {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    private Conversation firstConversation;
    private Conversation secondConversation;
    private Conversation thirdConversation;
    private User ceo;
    private User member;
    private User leader;

    @Before
    public void setUp() {
        ceo = save(UserTest.CEO);
        member = save(UserTest.MEMBER);
        leader = save(UserTest.LEADER);
        firstConversation = save(ConversationTest.FIRST_CONVERSATION, member, ceo);
        secondConversation = save(ConversationTest.SECOND_CONVERSATION, member, leader);
        thirdConversation = save(ConversationTest.THIRD_CONVERSATION, ceo, leader, member);
    }

    @Test
    public void find_by_user() {
        List<Conversation> conversations = conversationRepository.findByUsers(ceo);
        assertThat(conversations.equals(Arrays.asList(firstConversation, thirdConversation))).isTrue();
    }


    @Test
    public void find_by_user_id() {
        List<Conversation> conversations = conversationRepository.findByUserId(ceo.getId());
        assertThat(conversations.equals(Arrays.asList(firstConversation, thirdConversation))).isTrue();
    }




    private Conversation save(Conversation conversation, User... users) {
        return conversationRepository.save(new Conversation(conversation.getId(),
            conversation.getText(),
            conversation.getImageUrl(),
            conversation.isTeamMember(),
            conversation.isSameCompany(),
            conversation.isOtherCompany(),
            new HashSet<>(Arrays.asList(users))));
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
