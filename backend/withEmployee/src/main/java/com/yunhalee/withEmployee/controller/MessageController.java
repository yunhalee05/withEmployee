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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class MessageController {

    @Autowired
    private MessageService service;

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

    @PostMapping("/message/image")
    public String saveImages(@RequestParam("multipartFile") MultipartFile multipartFile){
        return service.saveImages(multipartFile);
    }
}
