package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.ConversationListDTO;
import com.yunhalee.withEmployee.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConversationController {

    @Autowired
    ConversationService service;

    @GetMapping("/conversations")
    public List<ConversationListDTO> listAll(@Param("id") Integer id){
       return service.listAll(id);
    }

    @PostMapping("/conversation")
    public ConversationListDTO createConversation(@RequestBody ConversationListDTO conversationListDTO){
        return service.createConversation(conversationListDTO);
    }

    @DeleteMapping("/conversation/{id}")
    public Integer deleteConversation(@PathVariable("id")Integer id){
        return service.deleteConversation(id);
    }
}
