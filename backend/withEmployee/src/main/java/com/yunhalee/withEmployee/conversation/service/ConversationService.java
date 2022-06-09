package com.yunhalee.withEmployee.conversation.service;

import com.yunhalee.withEmployee.common.exception.exceptions.AuthException;
import com.yunhalee.withEmployee.conversation.domain.ConversationRepository;
import com.yunhalee.withEmployee.conversation.dto.ConversationRequest;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponse;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponses;
import com.yunhalee.withEmployee.conversation.exception.ConversationNotFoundException;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.security.jwt.LoginUser;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.dto.CeoResponse;
import com.yunhalee.withEmployee.user.service.UserService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConversationService {

    private ConversationRepository conversationRepository;
    private UserService userService;

    public ConversationService(ConversationRepository conversationRepository,
        UserService userService) {
        this.conversationRepository = conversationRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public ConversationResponses listAll(Integer userId) {
        User user = userService.findUserById(userId);
        List<Conversation> conversations = conversationRepository.findAllByUsers(user);
        return ConversationResponses.of(conversations.stream()
            .map(conversation -> ConversationResponse.of(conversation, ceoResponses(conversation.getUsers())))
            .collect(Collectors.toList()));
    }

    public ConversationResponse create(ConversationRequest request) {
        Set<User> users = request.getUserEmails().stream()
            .map(email -> userService.findUserByEmail(email))
            .collect(Collectors.toSet());
        Conversation conversation = conversationRepository.save(request.toConversation().addUsers(users));
        return ConversationResponse.of(conversation, users.stream()
            .map(u -> CeoResponse.of(u))
            .collect(Collectors.toList()));
    }

    public void deleteConversation(LoginUser loginUser, Integer id) {
        Conversation conversation = findConversationById(id);
        checkUser(loginUser, conversation);
        conversationRepository.delete(conversation);
    }

    private void checkUser(LoginUser loginUser, Conversation conversation) {
        if (!conversation.isUserIncluded(loginUser.getId())) {
            throw new AuthException("User don't have authorization.");
        }
    }

    public Conversation findConversationById(Integer id) {
        return conversationRepository.findById(id)
            .orElseThrow(() -> new ConversationNotFoundException("Conversation does not exist with id : " + id));
    }

    private List<CeoResponse> ceoResponses(Set<User> users) {
        return users.stream()
            .map(user -> CeoResponse.of(user))
            .collect(Collectors.toList());
    }


}
