package com.yunhalee.withEmployee.dto;

import com.yunhalee.withEmployee.entity.Conversation;
import com.yunhalee.withEmployee.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ConversationListDTO {

    private Integer id;

    private String text;

    private String imageUrl;

    private List<ConversationUser> users;


    private boolean isTeamMember;

    private boolean isSameCompany;

    private boolean isOtherCompany;

    public ConversationListDTO(Conversation conversation){
        this.id = conversation.getId();
        this.text = conversation.getText();
        this.imageUrl = conversation.getImageUrl();
        this.users = ConversationUser.UserList(conversation.getUsers());
        this.isTeamMember = conversation.isTeamMember();
        this.isSameCompany = conversation.isSameCompany();
        this.isOtherCompany = conversation.isOtherCompany();
    }

    @Getter
    static class ConversationUser{
        private Integer id;

        private String name;

        private String imageUrl;

        static List<ConversationUser> UserList(Set<User> users){
            List<ConversationUser> conversationUsers = new ArrayList<>();
            users.forEach(user ->conversationUsers.add(new ConversationUser(user)) );
            return conversationUsers;
        }

        public ConversationUser(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.imageUrl = user.getImageUrl();
        }
    }



}
