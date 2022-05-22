package com.yunhalee.withEmployee.conversation.dto;

import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.conversation.dto.ConversationListDTO.ConversationUser;
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
    private boolean isTeamMember;
    private boolean isSameCompany;
    private boolean isOtherCompany;

    public ConversationResponse(Integer id, String text, String imageUrl, List<CeoResponse> users, boolean isTeamMember, boolean isSameCompany,
        boolean isOtherCompany) {
        this.id = id;
        this.text = text;
        this.imageUrl = imageUrl;
        this.users = users;
        this.isTeamMember = isTeamMember;
        this.isSameCompany = isSameCompany;
        this.isOtherCompany = isOtherCompany;
    }

    private ConversationResponse(Conversation conversation, List<CeoResponse> users) {
        this.id = conversation.getId();
        this.text = conversation.getText();
        this.imageUrl = conversation.getImageUrl();
        this.users = users;
        this.isTeamMember = conversation.isTeamMember();
        this.isSameCompany = conversation.isSameCompany();
        this.isOtherCompany = conversation.isOtherCompany();
    }

    public static ConversationResponse of(Conversation conversation, List<CeoResponse> users) {
        return new ConversationResponse(conversation, users);
    }

    @Override
    public String toString() {
        return "ConversationResponse{" +
            "id=" + id +
            ", text='" + text + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", users=" + users +
            ", isTeamMember=" + isTeamMember +
            ", isSameCompany=" + isSameCompany +
            ", isOtherCompany=" + isOtherCompany +
            '}';
    }
}
