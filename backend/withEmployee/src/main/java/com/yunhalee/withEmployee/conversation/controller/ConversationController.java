package com.yunhalee.withEmployee.conversation.controller;

import com.yunhalee.withEmployee.conversation.dto.ConversationRequest;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponse;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponses;
import com.yunhalee.withEmployee.conversation.service.ConversationService;
import com.yunhalee.withEmployee.security.AuthenticationPrincipal;
import com.yunhalee.withEmployee.security.jwt.LoginUser;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping("/conversations")
    public ResponseEntity<ConversationResponses> getAll(@Param("userId") Integer userId) {
        return ResponseEntity.ok(conversationService.listAll(userId));
    }


    @PostMapping("/conversations")
    public ResponseEntity<ConversationResponse> create(@RequestBody ConversationRequest request) {
        return ResponseEntity.ok(conversationService.create(request));
    }

    @DeleteMapping("/conversations/{id}")
    public ResponseEntity deleteConversation(@AuthenticationPrincipal LoginUser loginUser, @PathVariable("id") Integer id) {
        conversationService.deleteConversation(loginUser, id);
        return ResponseEntity.noContent().build();
    }
}
