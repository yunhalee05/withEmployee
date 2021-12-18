package com.yunhalee.withEmployee.message.domain;

import com.yunhalee.withEmployee.common.domain.BaseTimeEntity;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Getter
@Setter
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;

    private String content;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private com.yunhalee.withEmployee.user.domain.User User;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "to_user_id")
//    private User toUser;


    public Message() {
    }

    public Message(String content, Conversation conversation, com.yunhalee.withEmployee.user.domain.User user) {
        this.content = content;
        this.conversation = conversation;
        User = user;
    }

    public Message(String content, String imageName, String imageUrl, Conversation conversation, com.yunhalee.withEmployee.user.domain.User user) {
        this.content = content;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.conversation = conversation;
        User = user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", conversation=" + conversation +
                ", User=" + User +
                '}';
    }
}
