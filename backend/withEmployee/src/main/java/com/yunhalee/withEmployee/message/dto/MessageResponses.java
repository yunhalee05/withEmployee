package com.yunhalee.withEmployee.message.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageResponses {

    private List<MessageResponse> messages;

    private MessageResponses(List<MessageResponse> messages) {
        this.messages = messages;
    }

    public static MessageResponses of(List<MessageResponse> messages) {
        return new MessageResponses(messages);
    }
}
