package com.yunhalee.withEmployee.conversation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.yunhalee.withEmployee.MockBeans;
import com.yunhalee.withEmployee.common.exception.exceptions.AuthException;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.conversation.domain.ConversationTest;
import com.yunhalee.withEmployee.conversation.dto.ConversationRequest;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponse;
import com.yunhalee.withEmployee.security.jwt.LoginUser;
import com.yunhalee.withEmployee.user.domain.UserTest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;


class ConversationServiceTest extends MockBeans {

    private static final String USER_NOT_AUTHORIZED_EXCEPTION = "User don't have authorization.";

    @InjectMocks
    private ConversationService conversationService = new ConversationService(conversationRepository, userService);


    private ConversationRequest request;
    private Conversation conversation;

    @BeforeEach
    void setUp() {
        request = new ConversationRequest(ConversationTest.FIRST_CONVERSATION.getText(),
            ConversationTest.FIRST_CONVERSATION.getImageUrl(),
            ConversationTest.FIRST_CONVERSATION.isTeamMember(),
            ConversationTest.FIRST_CONVERSATION.isSameCompany(),
            ConversationTest.FIRST_CONVERSATION.isOtherCompany(),
            Arrays.asList(UserTest.CEO.getEmail(), UserTest.MEMBER.getEmail()));

        conversation = new Conversation(ConversationTest.FIRST_CONVERSATION.getId(),
            ConversationTest.FIRST_CONVERSATION.getText(),
            ConversationTest.FIRST_CONVERSATION.getImageUrl(),
            ConversationTest.FIRST_CONVERSATION.isTeamMember(),
            ConversationTest.FIRST_CONVERSATION.isSameCompany(),
            ConversationTest.FIRST_CONVERSATION.isOtherCompany(),
            new LinkedHashSet<>(Arrays.asList(UserTest.CEO, UserTest.MEMBER)));
    }


    @Test
    void create_conversation() {
        // when
        when(userService.findUserByEmail(UserTest.CEO.getEmail())).thenReturn(UserTest.CEO);
        when(userService.findUserByEmail(UserTest.MEMBER.getEmail())).thenReturn(UserTest.MEMBER);
        when(conversationRepository.save(any())).thenReturn(conversation);
        ConversationResponse response = conversationService.create(request);

        // then
        assertThat(response.getId()).isEqualTo(conversation.getId());
        assertThat(response.getUsers().size()).isEqualTo(2);
        List<String> emails = response.getUsers().stream().map(user -> user.getEmail()).collect(Collectors.toList());
        assertThat(emails.contains(UserTest.MEMBER.getEmail())).isTrue();
        assertThat(emails.contains(UserTest.CEO.getEmail())).isTrue();
    }


    @Test
    void delete_conversation_by_not_included_user_is_invalid() {
        when(conversationRepository.findById(any())).thenReturn(Optional.of(conversation));
        assertThatThrownBy(() -> conversationService
            .deleteConversation(LoginUser.of(UserTest.LEADER), conversation.getId()))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining(USER_NOT_AUTHORIZED_EXCEPTION);

    }

}
