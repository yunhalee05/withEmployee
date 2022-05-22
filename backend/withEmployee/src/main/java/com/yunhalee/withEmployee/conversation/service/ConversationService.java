package com.yunhalee.withEmployee.conversation.service;

import com.yunhalee.withEmployee.conversation.domain.ConversationRepository;
import com.yunhalee.withEmployee.conversation.dto.ConversationRequest;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponse;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponses;
import com.yunhalee.withEmployee.conversation.exception.ConversationNotFoundException;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.dto.CeoResponse;
import com.yunhalee.withEmployee.user.service.UserService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConversationService {

    private ConversationRepository conversationRepository;
    private UserService userService;

    public ConversationService(ConversationRepository conversationRepository, UserService userService) {
        this.conversationRepository = conversationRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public ConversationResponses listAll(Integer userId){
        User user = userService.findUserById(userId);
        System.out.println(user);
//        List<Conversation> conversations = repo.findByUsers(user);
//        List<ConversationListDTO> conversationListDTOS = new ArrayList<>();
//        conversations.forEach(conversation -> conversationListDTOS.add(new ConversationListDTO(conversation)));
        return ConversationResponses.of(conversationRepository.findByUsers(user).stream().map(conversation -> ConversationResponse.of(conversation, conversation.getUsers().stream().map(u -> CeoResponse.of(u)).collect(
            Collectors.toList()))).collect(Collectors.toList()));
    }

//    public ConversationListDTO createConversation(ConversationListDTO conversationListDTO){
//        Conversation conversation = new Conversation();
//
//        System.out.println(conversationListDTO.isTeamMember());
//        conversationListDTO.getUserEmails().forEach(email->{
//            conversation.addUser(userRepo.findByEmail(email).get());
//        });
//
//        conversation.setTeamMember(conversationListDTO.isTeamMember());
//        conversation.setSameCompany(conversationListDTO.isSameCompany());
//        conversation.setOtherCompany(conversationListDTO.isOtherCompany());
//
//        conversation.setImageUrl(conversationListDTO.getImageUrl());
//        conversation.setText(conversationListDTO.getText());
//
//        repo.save(conversation);
//
//        return new ConversationListDTO(conversation);
//    }


    public ConversationResponse create(ConversationRequest request) {
        Set<User> users = request.getUserEmails().stream()
            .map(email -> userService.findUserByEmail(email))
            .collect(Collectors.toSet());
        Conversation conversation = conversationRepository.save(request.toConversation().addUsers(users));
        return ConversationResponse.of(conversation, users.stream().map(u -> CeoResponse.of(u)).collect(
            Collectors.toList()));
    }

    public void deleteConversation(Integer id){
        conversationRepository.deleteById(id);
    }

    public Conversation findConversationById(Integer id){
        return conversationRepository.findById(id)
            .orElseThrow(() -> new ConversationNotFoundException("Conversation does not exist with id : " + id));
    }


}
