package com.yunhalee.withEmployee.conversation.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConversationResponses {

    private List<ConversationResponse> conversations;

    private ConversationResponses(List<ConversationResponse> conversations) {
        this.conversations = conversations;
    }

    public static ConversationResponses of(List<ConversationResponse> conversations){
        return new ConversationResponses(conversations);
    }
}
