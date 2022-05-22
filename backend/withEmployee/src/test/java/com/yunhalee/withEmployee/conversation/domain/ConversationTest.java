package com.yunhalee.withEmployee.conversation.domain;

import com.yunhalee.withEmployee.user.domain.UserTest;
import java.util.Arrays;
import java.util.HashSet;

public class ConversationTest {

    public static final Conversation FIRST_CONVERSATION = new Conversation(1,
        "firstConversation",
        "message/firstConversation.jpeg",
        true,
        false,
        false,
        new HashSet<>(Arrays.asList(UserTest.MEMBER, UserTest.CEO))
    );

    public static final Conversation SECOND_CONVERSATION = new Conversation(2,
        "secondConversation",
        "message/secondConversation.jpeg",
        false,
        true,
        false,
        new HashSet<>(Arrays.asList(UserTest.MEMBER, UserTest.LEADER))
    );

    public static final Conversation THIRD_CONVERSATION = new Conversation(3,
        "thirdConversation",
        "message/thirdConversation.jpeg",
        false,
        false,
        true,
        new HashSet<>(Arrays.asList(UserTest.CEO, UserTest.LEADER))
    );


}