package com.yunhalee.withEmployee.message.service;

import com.yunhalee.withEmployee.common.exception.exceptions.AuthException;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.conversation.service.ConversationService;
import com.yunhalee.withEmployee.message.domain.Message;
import com.yunhalee.withEmployee.message.domain.MessageRepository;
import com.yunhalee.withEmployee.message.dto.MessageRequest;
import com.yunhalee.withEmployee.message.dto.MessageResponse;
import com.yunhalee.withEmployee.message.dto.MessageResponses;
import com.yunhalee.withEmployee.message.dto.MessageUserResponse;
import com.yunhalee.withEmployee.security.jwt.LoginUser;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.service.UserService;
import com.yunhalee.withEmployee.util.FileUploadService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;
    private ConversationService conversationService;
    private UserService userService;
    private FileUploadService fileUploadService;

    public MessageService(MessageRepository messageRepository,
        ConversationService conversationService,
        UserService userService,
        FileUploadService fileUploadService) {
        this.messageRepository = messageRepository;
        this.conversationService = conversationService;
        this.userService = userService;
        this.fileUploadService = fileUploadService;
    }

    @Transactional(readOnly = true)
    public MessageResponses getMessages(Integer id) {
        Conversation conversation = conversationService.findConversationById(id);
        List<Message> messages = messageRepository.findByConversationOrderByCreatedAtDesc(conversation);
        return MessageResponses.of(messages.stream()
            .map(message -> MessageResponse.of(message, MessageUserResponse.of(message.getUser())))
            .collect(Collectors.toList()));
    }

    public MessageResponse create(LoginUser loginUser, MessageRequest request) {
        User user = userService.findUserById(request.getUserId());
        Conversation conversation = conversationService.findConversationById(request.getConversationId());
        checkConversationUser(loginUser.getId(), conversation);
        Message message = messageRepository.save(request.toMessage(conversation, user));
        return MessageResponse.of(message, MessageUserResponse.of(user));
    }

    private void checkConversationUser(Integer userId, Conversation conversation) {
        if (!conversation.isUserIncluded(userId)) {
            throw new AuthException("User don't have authorization. This User is not a part of this conversation.");
        }
    }

    public void delete(LoginUser loginUser, Integer id) {
        Message message = messageRepository.findById(id)
            .orElseThrow(() -> new MessageConversionException("Message not found with id : " + id));
        checkMessageUser(loginUser, message);
        messageRepository.delete(message);
    }

    private void checkMessageUser(LoginUser loginUser, Message message) {
        if (!message.isWriter(loginUser.getId())) {
            throw new AuthException("User don't have authorization. This User is not message writer.");
        }
    }

    public String saveImages(MultipartFile multipartFile) {
        return fileUploadService.uploadMessageImage(multipartFile);
    }


}
