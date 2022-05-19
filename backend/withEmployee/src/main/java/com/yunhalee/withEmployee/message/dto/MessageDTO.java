package com.yunhalee.withEmployee.message.dto;

import com.yunhalee.withEmployee.message.domain.Message;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.Setter;

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

    public MessageDTO(Integer id) {
        this.id = id;
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