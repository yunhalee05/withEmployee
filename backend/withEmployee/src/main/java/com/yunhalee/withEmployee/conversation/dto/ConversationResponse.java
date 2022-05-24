package com.yunhalee.withEmployee.conversation.dto;

import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.user.dto.CeoResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConversationResponse {

    private Integer id;
    private String text;
    private String imageUrl;
    private List<CeoResponse> users;
    private boolean teamMember;
    private boolean sameCompany;
    private boolean otherCompany;

    public ConversationResponse(Integer id, String text, String imageUrl, List<CeoResponse> users, boolean teamMember, boolean sameCompany, boolean otherCompany) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.users = users;
        this.teamMember = teamMember;
        this.sameCompany = sameCompany;
        this.otherCompany = otherCompany;
    }

    private ConversationResponse(Conversation conversation, List<CeoResponse> users) {
        this.id = conversation.getId();
        this.text = conversation.getText();
        this.imageUrl = conversation.getImageUrl();
        this.users = users;
        this.teamMember = conversation.isTeamMember();
        this.sameCompany = conversation.isSameCompany();
        this.otherCompany = conversation.isOtherCompany();
    }

    public static ConversationResponse of(Conversation conversation, List<CeoResponse> users) {
        return new ConversationResponse(conversation, users);
    }

}
