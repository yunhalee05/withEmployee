package com.yunhalee.withEmployee.conversation.service;

import com.yunhalee.withEmployee.conversation.domain.ConversationRepository;
import com.yunhalee.withEmployee.conversation.dto.ConversationListDTO;
import com.yunhalee.withEmployee.conversation.service.ConversationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ConversationServiceTests {

    @Autowired
    ConversationService conversationService;

    @Autowired
    ConversationRepository conversationRepository;

    @Test
    public void CreateConversation(){
        //given
        List<String> userEmails = new ArrayList<>();
        userEmails.add("member1@example.com");
        userEmails.add("member2@example.com");
        ConversationListDTO conversationListDTO = new ConversationListDTO("testConversation", "",true,userEmails);

        //when
        ConversationListDTO conversationListDTO1 = conversationService.createConversation(conversationListDTO);

        //then
        assertNotNull(conversationListDTO1.getId());
    }

    @Test
    public void deleteConversation(){
        //given
        Integer id = 1;

        //when
        conversationService.deleteConversation(id);

        //then
        assertNull(conversationRepository.findById(id));
    }

    @Test
    public void getAllConversationsByUserId(){
        //given
        Integer userId = 17;

        //when
        List<ConversationListDTO> conversationListDTOS = conversationService.listAll(userId);

        //then
        assertNotEquals(conversationListDTOS.size(),0);
    }
}
