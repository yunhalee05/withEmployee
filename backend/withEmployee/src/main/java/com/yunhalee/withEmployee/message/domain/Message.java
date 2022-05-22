package com.yunhalee.withEmployee.message.domain;

import com.yunhalee.withEmployee.common.domain.BaseTimeEntity;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.user.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message")
@Getter
@NoArgsConstructor
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;

    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Message(String content, Conversation conversation, User user) {
        this.content = content;
        this.conversation = conversation;
        this.user = user;
    }

    public Message(String content, String imageUrl, Conversation conversation, User user) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.conversation = conversation;
        this.user = user;
    }

    public static Message of(String content, String imageUrl, Conversation conversation, User user) {
        return new Message(content, imageUrl, conversation, user);
    }

    public Integer getConversationId() {
        return this.conversation.getId();
    }

}
