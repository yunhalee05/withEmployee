package com.yunhalee.withEmployee.message.domain;

import com.yunhalee.withEmployee.conversation.domain.ConversationTest;
import com.yunhalee.withEmployee.user.domain.UserTest;

public class MessageTest {

    public static final Message FIRST_MESSAGE = Message.builder()
        .id(1)
        .content("firstMessage")
        .conversation(ConversationTest.FIRST_CONVERSATION)
        .user(UserTest.MEMBER)
        .build();


    public static final Message SECOND_MESSAGE = Message.builder()
        .id(2)
        .content("secondMessage")
        .conversation(ConversationTest.FIRST_CONVERSATION)
        .user(UserTest.CEO)
        .build();
}