package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.FileUploadUtils;
import com.yunhalee.withEmployee.Repository.ConversationRepository;
import com.yunhalee.withEmployee.Repository.MessageRepository;
import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.dto.MessageDTO;
import com.yunhalee.withEmployee.entity.Conversation;
import com.yunhalee.withEmployee.entity.Message;
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
public class MessageService {

    @Autowired
    private MessageRepository repo;

    @Autowired
    private ConversationRepository conversationRepo;

    @Autowired
    private UserRepository userRepo;

    public List<MessageDTO> getMessages(Integer id){
        Conversation conversation = conversationRepo.findById(id).get();
        List<Message> messages = repo.findByConversationOrderByCreatedAtDesc(conversation);
        List<MessageDTO> messageDTOS = new ArrayList<>();
        messages.forEach(message -> messageDTOS.add(new MessageDTO(message)));
        return messageDTOS;
    }

    public MessageDTO createMessage(MessageDTO messageDTO){
        User user = userRepo.findById(messageDTO.getUserId()).get();
        Conversation conversation = conversationRepo.findById(messageDTO.getConversationId()).get();
        conversation.setText(messageDTO.getContent());
        conversation.setImageUrl(messageDTO.getImageUrl());
        conversationRepo.save(conversation);
        Message message = new Message();
        message.setUser(user);
        message.setConversation(conversation);
        if(messageDTO.getContent()!=null){
            message.setContent(messageDTO.getContent());
        }
        if(messageDTO.getImageUrl()!=null){
            message.setImageUrl(messageDTO.getImageUrl());
        }
        repo.save(message);
        return new MessageDTO(message);
    }

    public Integer deleteMessage(Integer id){
        repo.deleteById(id);
        return id;
    }

    public String saveImages(MultipartFile multipartFile){
        String fileName = System.currentTimeMillis()+ StringUtils.cleanPath(multipartFile.getOriginalFilename());

        try{
            String uploadDir = "messageUploads";
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
        }catch (IOException e){
            new IOException("Could not save file : " + multipartFile.getOriginalFilename());
        }

        return "/messageUploads/"+ fileName;

    }
}
