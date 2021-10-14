package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.MessageRepository;
import com.yunhalee.withEmployee.dto.MessageDTO;
import com.yunhalee.withEmployee.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MessageServiceTests {

    @Autowired
    MessageService messageService;

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void createMessage(){
        //given
        String content = "testMessage";
        Integer conversationId = 1;
        Integer userId = 17;
        MessageDTO messageDTO = new MessageDTO(content,conversationId,userId);

        //when
        MessageDTO messageDTO1 = messageService.createMessage(messageDTO);

        //then
        assertNotNull(messageDTO1.getId());
        assertEquals(messageDTO1.getContent(), content);
        assertEquals(messageDTO1.getConversationId(),conversationId);
        assertEquals(messageDTO1.getUserId(),userId);
    }

    @Test
    public void deleteMessage(){
        //given
        Integer id = 1;

        //when
        messageService.deleteMessage(id);

        //then
        assertNull(messageRepository.findById(id));
    }

    @Test
    public void getMessages(){
        //given
        Integer conversationId = 1;

        //when
        List<MessageDTO> messageDTOS = messageService.getMessages(conversationId);

        //then
        assertNotEquals(0, messageDTOS.size());
    }

}
