package com.yunhalee.withEmployee.message.dto;

import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.message.domain.Message;
import com.yunhalee.withEmployee.message.dto.MessageDTO.MessageUser;
import com.yunhalee.withEmployee.user.domain.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRequest {

    private String content;
    private String imageUrl;
    private Integer conversationId;
    private Integer userId;

    public MessageRequest(String content, String imageUrl, Integer conversationId, Integer userId) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.conversationId = conversationId;
        this.userId = userId;
    }

    public Message toMessage(Conversation conversation, User user) {
        return Message.of(content, imageUrl, conversation, user);
    }
}
