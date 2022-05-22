package com.yunhalee.withEmployee.conversation.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.EntityNotFoundException;

public class ConversationNotFoundException extends EntityNotFoundException {

    public ConversationNotFoundException(String message) {
        super(message);
    }

    public ConversationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversationNotFoundException(Throwable cause) {
        super(cause);
    }
}
