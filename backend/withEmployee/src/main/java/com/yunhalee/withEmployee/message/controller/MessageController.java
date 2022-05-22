package com.yunhalee.withEmployee.message.controller;

import com.yunhalee.withEmployee.message.dto.MessageRequest;
import com.yunhalee.withEmployee.message.dto.MessageResponse;
import com.yunhalee.withEmployee.message.dto.MessageResponses;
import com.yunhalee.withEmployee.message.service.MessageService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<MessageResponses> getAll(@Param("conversationId") Integer conversationId) {
        return ResponseEntity.ok(messageService.getMessages(conversationId));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> create(@RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.create(request));
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/messages/image")
    public ResponseEntity saveImage(@RequestParam("multipartFile") MultipartFile multipartFile) {
        return ResponseEntity.ok(messageService.saveImages(multipartFile));
    }
}
