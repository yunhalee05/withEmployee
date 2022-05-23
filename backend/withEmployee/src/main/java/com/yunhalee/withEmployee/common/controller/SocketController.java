package com.yunhalee.withEmployee.common.controller;

import com.yunhalee.withEmployee.conversation.dto.ConversationResponse;
import com.yunhalee.withEmployee.message.dto.MessageResponse;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocketController {


    private static Set<Integer> userList = new HashSet<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{id}")
    public void sendMessage(@Payload MessageResponse response, @DestinationVariable Integer id) {
        this.simpMessagingTemplate.convertAndSend("/queue/addChatToClient/" + id, response);
    }

    @MessageMapping("/chat/delete/{id}")
    public void deleteMessage(@Payload Integer messageId, @DestinationVariable Integer id) {
        this.simpMessagingTemplate.convertAndSend("/queue/deleteChatToClient/" + id, messageId);
    }

    @MessageMapping("/chat/addConversation/{id}")
    public void addConversation(@Payload ConversationResponse response,
        @DestinationVariable Integer id) {
        this.simpMessagingTemplate.convertAndSend("/queue/addConversationToClient/" + id, response);
    }


    @MessageMapping("/chat/deleteConversation/{id}")
    public void deleteConversation(@Payload Integer conversationId, @DestinationVariable Integer id) {
        this.simpMessagingTemplate
            .convertAndSend("/queue/deleteConversationToClient/" + id, conversationId);
    }


    @MessageMapping("/join")
    public void joinUser(@Payload Integer userId) {
        userList.add(userId);
    }
}
