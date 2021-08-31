package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.MessageDTO;
import com.yunhalee.withEmployee.entity.Message;
import com.yunhalee.withEmployee.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class MessageController {

    private static Set<Integer> userList = new HashSet<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService service;

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

    @MessageMapping("/join")
    public void joinUser(@Payload Integer userId){
        userList.add(userId);
        userList.forEach(user-> System.out.println(user));
    }

    @GetMapping("/messages/{id}")
    public List<MessageDTO> getMessages(@PathVariable("id")Integer id){
        return service.getMessages(id);
    }

    @PostMapping("/message")
    public MessageDTO createMessage(@RequestBody MessageDTO messageDTO){
        return service.createMessage(messageDTO);
    }

    @DeleteMapping("/message/delete")
    public Integer deleteMessage(@RequestParam("id")Integer id){
        return service.deleteMessage(id);
    }
}
