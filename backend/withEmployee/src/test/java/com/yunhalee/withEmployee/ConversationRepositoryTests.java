package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.ConversationRepository;
import com.yunhalee.withEmployee.entity.Conversation;
import com.yunhalee.withEmployee.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ConversationRepositoryTests {

    @Autowired
    private ConversationRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createConversationByUserTest(){
        User user1 = entityManager.find(User.class, 34);
        User user2 = entityManager.find(User.class, 31);

        Conversation conversation = new Conversation();

        conversation.addUser(user1);
        conversation.addUser(user2);

        repo.save(conversation);
        System.out.println(conversation);

    }

    @Test
    public void getConversationsByUserId(){
        List<Conversation> conversations = repo.fintByUserId(34);
        conversations.forEach(conversation -> System.out.println(conversation));
    }

    @Test
    public void getConversations(){
        List<Conversation> conversations = (List<Conversation>) repo.findAll();
        conversations.forEach(conversation -> System.out.println(conversation));
    }

    @Test
    public void getConversationsByUser(){
        User user = entityManager.find(User.class, 34);
        List<Conversation> conversations = repo.findByUsers(java.util.Optional.ofNullable(user));

        conversations.forEach(conversation -> System.out.println(conversation));
    }

}
