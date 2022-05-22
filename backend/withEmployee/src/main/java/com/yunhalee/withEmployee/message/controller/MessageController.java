package com.yunhalee.withEmployee.message.controller;

import com.yunhalee.withEmployee.message.dto.MessageDTO;
import com.yunhalee.withEmployee.message.dto.MessageRequest;
import com.yunhalee.withEmployee.message.dto.MessageResponse;
import com.yunhalee.withEmployee.message.dto.MessageResponses;
import com.yunhalee.withEmployee.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService service;

    @GetMapping("/messages")
    public ResponseEntity<MessageResponses> getAll(@Param("conversationId") Integer conversationId){
        return ResponseEntity.ok(service.getMessages(conversationId));
    }

//    @PostMapping("/message")
//    public MessageDTO createMessage(@RequestBody MessageDTO messageDTO){
//        return service.createMessage(messageDTO);
//    }

    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> create(@RequestBody MessageRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity delete(@PathVariable("id")Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/messages/image")
    public ResponseEntity saveImage(@RequestParam("multipartFile") MultipartFile multipartFile){
        return ResponseEntity.ok(service.saveImages(multipartFile));
    }
}
