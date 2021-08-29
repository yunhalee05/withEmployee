package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.ConversationRepository;
import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.dto.ConversationListDTO;
import com.yunhalee.withEmployee.entity.Conversation;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository repo;

    @Autowired
    private UserRepository userRepo;

    public List<ConversationListDTO> listAll(Integer id){
        Optional<User> user = userRepo.findById(id);
        List<Conversation> conversations = repo.findByUsers(user);
        List<ConversationListDTO> conversationListDTOS = new ArrayList<>();
        conversations.forEach(conversation -> conversationListDTOS.add(new ConversationListDTO(conversation)));
        return conversationListDTOS;
    }

    public ConversationListDTO createConversation(String toEmail, String fromEmail){
        Conversation conversation = new Conversation();
        User user1 = userRepo.findByEmail(toEmail);
        User user2 = userRepo.findByEmail(fromEmail);

        conversation.addUser(user1);
        conversation.addUser(user2);
        repo.save(conversation);

        return new ConversationListDTO(conversation);
    }
}
