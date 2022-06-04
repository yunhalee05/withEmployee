package com.yunhalee.withEmployee.message.domain;

import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.conversation.domain.ConversationRepository;
import com.yunhalee.withEmployee.conversation.domain.ConversationTest;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.user.domain.UserTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/config/application-test.properties")
public class MessageRepositoryTests {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    private Message firstMessage;
    private Message secondMessage;
    private Conversation conversation;
    private User ceo;
    private User member;

    @Before
    public void setUp() {
        ceo = userRepository.save(UserTest.CEO);
        member = userRepository.save(UserTest.MEMBER);
        conversation = Conversation.of(ConversationTest.FIRST_CONVERSATION.getText(),
            ConversationTest.FIRST_CONVERSATION.getImageUrl(),
            ConversationTest.FIRST_CONVERSATION.isTeamMember(),
            ConversationTest.FIRST_CONVERSATION.isSameCompany(),
            ConversationTest.FIRST_CONVERSATION.isOtherCompany()
        );
        conversation.addUsers(new HashSet<>(Arrays.asList(ceo, member)));
        conversationRepository.save(conversation);
        firstMessage = messageRepository.save(Message.builder()
            .content(MessageTest.FIRST_MESSAGE.getContent())
            .conversation(conversation)
            .user(member).build());
        secondMessage = messageRepository.save(Message.builder()
            .content(MessageTest.SECOND_MESSAGE.getContent())
            .conversation(conversation)
            .user(ceo).build());
    }

    @Test
    public void find_messages_by_conversation_order_by_created_at_desc() {
        // when
        List<Message> messages = messageRepository.findByConversationOrderByCreatedAtDesc(conversation);

        // then
        LocalDateTime time = LocalDateTime.now();
        for (Message message : messages) {
            assertThat(message.getConversation()).isEqualTo(conversation);
            assertThat(time).isAfter(message.getCreatedAt());
            time = message.getCreatedAt();
        }
    }

}
