package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.ConversationListDTO;
import com.yunhalee.withEmployee.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ConversationListDTO createConversation(@Param("toEmail") String toEmail, @Param("fromEmail") String fromEmail){
        return service.createConversation(toEmail, fromEmail);
    }
}
