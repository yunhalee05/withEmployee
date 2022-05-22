package com.yunhalee.withEmployee.conversation.dto;

import com.yunhalee.withEmployee.conversation.domain.Conversation;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConversationRequest {

    private String text;
    private String imageUrl;
    private boolean teamMember;
    private boolean sameCompany;
    private boolean otherCompany;
    private List<String> userEmails;

    public ConversationRequest(String text, String imageUrl, boolean teamMember,
        boolean sameCompany, boolean isOtherCompany, List<String> userEmails) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.teamMember = teamMember;
        this.sameCompany = sameCompany;
        this.otherCompany = isOtherCompany;
        this.userEmails = userEmails;
    }

    public Conversation toConversation() {
        return Conversation.of(text, imageUrl, teamMember, sameCompany, otherCompany);
    }
}
