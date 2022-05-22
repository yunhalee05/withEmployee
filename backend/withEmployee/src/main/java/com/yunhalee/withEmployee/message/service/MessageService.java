package com.yunhalee.withEmployee.message.service;

import com.yunhalee.withEmployee.conversation.service.ConversationService;
import com.yunhalee.withEmployee.message.dto.MessageRequest;
import com.yunhalee.withEmployee.message.dto.MessageResponse;
import com.yunhalee.withEmployee.message.dto.MessageResponses;
import com.yunhalee.withEmployee.message.dto.MessageUserResponse;
import com.yunhalee.withEmployee.user.service.UserService;
import com.yunhalee.withEmployee.util.FileUploadUtils;
import com.yunhalee.withEmployee.message.domain.MessageRepository;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.message.domain.Message;
import com.yunhalee.withEmployee.user.domain.User;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;
    private ConversationService conversationService;
    private UserService userService;

    public MessageService(MessageRepository messageRepository,
        ConversationService conversationService,
        UserService userService) {
        this.messageRepository = messageRepository;
        this.conversationService = conversationService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public MessageResponses getMessages(Integer id){
//        Conversation conversation = conversationRepo.findById(id).get();
        Conversation conversation = conversationService.findConversationById(id);
//        List<Message> messages = repo.findByConversationOrderByCreatedAtDesc(conversation);
//        List<MessageDTO> messageDTOS = new ArrayList<>();
//        messages.forEach(message -> messageDTOS.add(new MessageDTO(message)));
        return MessageResponses.of(
            messageRepository.findByConversationOrderByCreatedAtDesc(conversation).stream().map(
            message -> MessageResponse.of(message, MessageUserResponse.of(message.getUser()))).collect(
            Collectors.toList()));

    }

//    public MessageDTO createMessage(MessageDTO messageDTO){
//        User user = userRepo.findById(messageDTO.getUserId()).get();
//        Conversation conversation = conversationRepo.findById(messageDTO.getConversationId()).get();
////        conversation.setText(messageDTO.getContent());
////        conversation.setImageUrl(messageDTO.getImageUrl());
//        conversationRepo.save(conversation);
//        Message message = new Message();
//        message.setUser(user);
//        message.setConversation(conversation);
//        if(messageDTO.getContent()!=null){
//            message.setContent(messageDTO.getContent());
//        }
//        if(messageDTO.getImageUrl()!=null){
//            message.setImageUrl(messageDTO.getImageUrl());
//        }
//        repo.save(message);
//        return new MessageDTO(message);
//    }
    public MessageResponse create(MessageRequest request) {
        User user = userService.findUserById(request.getUserId());
        Conversation conversation = conversationService.findConversationById(request.getConversationId());
        Message message = messageRepository.save(request.toMessage(conversation, user));
        return MessageResponse.of(message, MessageUserResponse.of(user));
    }

    public void delete(Integer id){
        messageRepository.deleteById(id);
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
