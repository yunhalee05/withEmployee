package com.yunhalee.withEmployee.message.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.yunhalee.withEmployee.MockBeans;
import com.yunhalee.withEmployee.message.domain.Message;
import com.yunhalee.withEmployee.message.domain.MessageTest;
import com.yunhalee.withEmployee.message.dto.MessageRequest;
import com.yunhalee.withEmployee.message.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class MessageServiceTest extends MockBeans {

    @InjectMocks
    private MessageService messageService = new MessageService(messageRepository,
        conversationService,
        userService,
        fileUploadService);

    private MessageRequest request;
    private Message message;

    @BeforeEach
    void setUp() {
        request = new MessageRequest(MessageTest.FIRST_MESSAGE.getContent(),
            MessageTest.FIRST_MESSAGE.getImageUrl(),
            MessageTest.FIRST_MESSAGE.getConversationId(),
            MessageTest.FIRST_MESSAGE.getUser().getId());

        message = Message.builder()
            .id(MessageTest.FIRST_MESSAGE.getId())
            .content(MessageTest.FIRST_MESSAGE.getContent())
            .conversation(MessageTest.FIRST_MESSAGE.getConversation())
            .user(MessageTest.FIRST_MESSAGE.getUser())
            .build();
    }

    @Test
    void create_message() {
        // when
        when(userService.findUserById(any())).thenReturn(MessageTest.FIRST_MESSAGE.getUser());
        when(conversationService.findConversationById(any())).thenReturn(MessageTest.FIRST_MESSAGE.getConversation());
        when(messageRepository.save(any())).thenReturn(message);
        MessageResponse response = messageService.create(request);

        // then
        equals(response, message);

    }

    private void equals(MessageResponse response, Message message) {
        assertThat(response.getId()).isEqualTo(message.getId());
        assertThat(response.getContent()).isEqualTo(message.getContent());
        assertThat(response.getImageUrl()).isEqualTo(message.getImageUrl());
        assertThat(response.getConversationId()).isEqualTo(message.getConversationId());
        assertThat(response.getUser().getId()).isEqualTo(message.getUser().getId());
    }

}
