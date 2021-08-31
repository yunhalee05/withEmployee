package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.FileUploadUtils;
import com.yunhalee.withEmployee.Repository.ConversationRepository;
import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.dto.ConversationListDTO;
import com.yunhalee.withEmployee.entity.Conversation;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public ConversationListDTO createConversation(ConversationListDTO conversationListDTO){
        Conversation conversation = new Conversation();

        System.out.println(conversationListDTO.isTeamMember());
        conversationListDTO.getUserEmails().forEach(email->{
            conversation.addUser(userRepo.findByEmail(email));
        });

        conversation.setTeamMember(conversationListDTO.isTeamMember());
        conversation.setSameCompany(conversationListDTO.isSameCompany());
        conversation.setOtherCompany(conversationListDTO.isOtherCompany());

        conversation.setImageUrl(conversationListDTO.getImageUrl());
        conversation.setText(conversationListDTO.getText());

        repo.save(conversation);

        return new ConversationListDTO(conversation);
    }

    public Integer deleteConversation(Integer id){
        repo.deleteById(id);
        return id;
    }



}
