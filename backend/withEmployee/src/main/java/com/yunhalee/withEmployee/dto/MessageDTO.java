package com.yunhalee.withEmployee.dto;

import com.yunhalee.withEmployee.entity.Conversation;
import com.yunhalee.withEmployee.entity.Message;
import com.yunhalee.withEmployee.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDTO {

    private Integer id;

    private String imageUrl;

    private String content;

    private Integer conversationId;

    private Integer userId;

    private MessageUser user;

    private LocalDateTime createdAt;

    public MessageDTO() {
    }

    public MessageDTO(String content, Integer conversationId, Integer userId) {
        this.content = content;
        this.conversationId = conversationId;
        this.userId = userId;
    }

    public MessageDTO(String imageUrl, String content, Integer conversationId, Integer userId) {
        this.imageUrl = imageUrl;
        this.content = content;
        this.conversationId = conversationId;
        this.userId = userId;
    }

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.imageUrl = message.getImageUrl();
        this.conversationId = message.getConversation().getId();
        this.user = new MessageUser(message.getUser());
        this.createdAt = message.getCreatedAt();
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                '}';
    }

    @Getter
    static class MessageUser{
        private Integer id;

        private String name;

        private String imageUrl;

        public MessageUser() {
        }

        public MessageUser(Integer id, String name, String imageUrl) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public MessageUser(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.imageUrl = user.getImageUrl();
        }
    }

}
