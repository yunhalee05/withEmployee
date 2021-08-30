package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.MessageRepository;
import com.yunhalee.withEmployee.entity.Conversation;
import com.yunhalee.withEmployee.entity.Message;
import com.yunhalee.withEmployee.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MessageRepositoryTests {

    @Autowired
    private MessageRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createMessageTest(){

        Conversation conversation = entityManager.find(Conversation.class, 1);
        User user = entityManager.find(User.class,34);
        Message message = new Message("HelloThere", conversation, user );
        repo.save(message);

        System.out.println(message);
    }

    @Test
    public void findAllSortByCreatedAt(){
        List<Message> messages = repo.findAllByOrderByCreatedAtDesc();
        messages.forEach(message -> System.out.println(message));
    }

    @Test
    public void findByConversationByCreatedAt(){
        Conversation conversation = entityManager.find(Conversation.class, 1);
        List<Message> messages = repo.findByConversationOrderByCreatedAtDesc(conversation);
        messages.forEach(message -> System.out.println(message));
    }

    @Test
    public void deleteMessageById(){
        repo.deleteById(4);

    }
}
