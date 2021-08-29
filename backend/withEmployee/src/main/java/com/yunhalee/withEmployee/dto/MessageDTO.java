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

@Getter
@Setter
public class MessageDTO {

    private Integer id;

    private String imageUrl;

    private String content;

    private Integer ConversationId;

    private MessageUser fromUser;

    private MessageUser toUser;

    public MessageDTO() {
    }

    public MessageDTO(String content, Integer conversationId, MessageUser fromUser, MessageUser toUser) {
        this.content = content;
        ConversationId = conversationId;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.imageUrl = message.getImageUrl();
        ConversationId = message.getConversation().getId();
        this.fromUser = new MessageUser(message.getFromUser());
        this.toUser = new MessageUser(message.getToUser());
    }

    @Getter
    static class MessageUser{
        private Integer id;

        private String name;

        private String imageUrl;

        public MessageUser(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.imageUrl = user.getImageUrl();
        }
    }

}
