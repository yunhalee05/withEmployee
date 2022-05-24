package com.yunhalee.withEmployee.message.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.EntityNotFoundException;

public class MessageNotFoundException extends EntityNotFoundException {

    public MessageNotFoundException(String message) {
        super(message);
    }

    public MessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageNotFoundException(Throwable cause) {
        super(cause);
    }
}
