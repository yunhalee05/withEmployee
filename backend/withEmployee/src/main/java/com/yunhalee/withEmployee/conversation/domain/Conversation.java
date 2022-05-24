package com.yunhalee.withEmployee.conversation.domain;

import com.yunhalee.withEmployee.common.domain.BaseTimeEntity;
import com.yunhalee.withEmployee.message.domain.Message;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "conversation")
@Getter
@NoArgsConstructor
public class Conversation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private Integer id;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    private String text;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_team_member")
    private boolean isTeamMember;

    @Column(name = "is_same_company")
    private boolean isSameCompany;

    @Column(name = "is_other_company")
    private boolean isOtherCompany;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "conversation_user",
        joinColumns = @JoinColumn(name = "conversation_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    public Conversation(Integer id, String text, String imageUrl, boolean isTeamMember, boolean isSameCompany, boolean isOtherCompany, Set<User> users) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.isTeamMember = isTeamMember;
        this.isSameCompany = isSameCompany;
        this.isOtherCompany = isOtherCompany;
        this.users = users;
    }

    private Conversation(String text, String imageUrl, boolean isTeamMember, boolean isSameCompany, boolean isOtherCompany) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.isTeamMember = isTeamMember;
        this.isSameCompany = isSameCompany;
        this.isOtherCompany = isOtherCompany;
    }

    public static Conversation of(String text, String imageUrl, boolean isTeamMember, boolean isSameCompany, boolean otherCompany) {
        return new Conversation(text, imageUrl, isTeamMember, isSameCompany, otherCompany);
    }

    public Conversation addUsers(Set<User> users) {
        users.stream().forEach(user -> addUser(user));
        return this;
    }

    private void addUser(User user) {
        this.users.add(user);
    }

    public void changeLatestTextAndImage(String text, String imageUrl) {
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public boolean isUserIncluded(Integer userId) {
        return users.stream()
            .anyMatch(user -> user.isUser(userId));
    }

}
