package com.yunhalee.withEmployee.message.dto;

import com.yunhalee.withEmployee.message.domain.Message;
import com.yunhalee.withEmployee.message.dto.MessageDTO.MessageUser;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageResponse {

    private Integer id;
    private String imageUrl;
    private String content;
    private Integer conversationId;
    private MessageUserResponse user;
    private LocalDateTime createdAt;

    public MessageResponse(Integer id, String imageUrl, String content, Integer conversationId, MessageUserResponse user, LocalDateTime createdAt) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.content = content;
        this.conversationId = conversationId;
        this.user = user;
        this.createdAt = createdAt;
    }

    private MessageResponse(Message message, MessageUserResponse user) {
        this.id = message.getId();
        this.content = message.getContent();
        this.imageUrl = message.getImageUrl();
        this.conversationId = message.getConversationId();
        this.user = user;
        this.createdAt = message.getCreatedAt();
    }

    public static MessageResponse of(Message message, MessageUserResponse user) {
        return new MessageResponse(message, user);
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
            "id=" + id +
            ", imageUrl='" + imageUrl + '\'' +
            ", content='" + content + '\'' +
            ", conversationId=" + conversationId +
            ", user=" + user +
            ", createdAt=" + createdAt +
            '}';
    }
}
