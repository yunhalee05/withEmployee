package com.yunhalee.withEmployee.conversation.dto;

import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.user.domain.User;
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

    private List<String> userEmails;

    public ConversationListDTO() {
    }

    public ConversationListDTO(String text, String imageUrl, boolean isTeamMember, boolean isSameCompany, boolean isOtherCompany, List<String> userEmails) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.isTeamMember = isTeamMember;
        this.isSameCompany = isSameCompany;
        this.isOtherCompany = isOtherCompany;
        this.userEmails = userEmails;
    }

    public ConversationListDTO(String text, String imageUrl, boolean isTeamMember, List<String> userEmails) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.isTeamMember = isTeamMember;
        this.userEmails = userEmails;
    }

    public ConversationListDTO(Integer id, String text, String imageUrl, List<ConversationUser> users, boolean isTeamMember, boolean isSameCompany, boolean isOtherCompany) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.users = users;
        this.isTeamMember = isTeamMember;
        this.isSameCompany = isSameCompany;
        this.isOtherCompany = isOtherCompany;
    }

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

        private List<String> companies;

        static List<ConversationUser> UserList(Set<User> users){
            List<ConversationUser> conversationUsers = new ArrayList<>();
            users.forEach(user ->conversationUsers.add(new ConversationUser(user)) );
            return conversationUsers;
        }

        public ConversationUser() {
        }

        public ConversationUser(Integer id, String name, String imageUrl) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public ConversationUser(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.imageUrl = user.getImageUrl();
            this.companies = user.getCompanyNames();
        }
    }




}
