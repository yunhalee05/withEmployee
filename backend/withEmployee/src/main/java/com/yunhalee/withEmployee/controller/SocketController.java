package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.ConversationListDTO;
import com.yunhalee.withEmployee.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class SocketController {


    private static Set<Integer> userList = new HashSet<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{id}")
    public void sendMessage(@Payload MessageDTO messageDTO, @DestinationVariable Integer id){
        this.simpMessagingTemplate.convertAndSend("/queue/addChatToClient/"+id,messageDTO);
//        System.out.println(messageDTO);
//        System.out.println(id);
    }

    @MessageMapping("/chat/delete/{id}")
    public void deleteMessage(@Payload Integer messageId, @DestinationVariable Integer id){
        this.simpMessagingTemplate.convertAndSend("/queue/deleteChatToClient/"+id,messageId);
//        System.out.println(messageId);
//        System.out.println(id);
    }

    @MessageMapping("/chat/addConversation/{id}")
    public void addConversation(@Payload ConversationListDTO conversationListDTO, @DestinationVariable Integer id){
        this.simpMessagingTemplate.convertAndSend("/queue/addConversationToClient/"+id,conversationListDTO);
        System.out.println(conversationListDTO);
        System.out.println(id);
    }


    @MessageMapping("/chat/deleteConversation/{id}")
    public void deleteConversation(@Payload Integer conversationId, @DestinationVariable Integer id){
        this.simpMessagingTemplate.convertAndSend("/queue/deleteConversationToClient/"+id,conversationId);
//        System.out.println(conversationId);
//        System.out.println(id);
    }



    @MessageMapping("/join")
    public void joinUser(@Payload Integer userId){
        userList.add(userId);
        userList.forEach(user-> System.out.println(user));
    }
}
