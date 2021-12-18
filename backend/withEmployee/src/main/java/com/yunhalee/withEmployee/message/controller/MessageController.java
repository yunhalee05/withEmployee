package com.yunhalee.withEmployee.message.controller;

import com.yunhalee.withEmployee.message.dto.MessageDTO;
import com.yunhalee.withEmployee.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
