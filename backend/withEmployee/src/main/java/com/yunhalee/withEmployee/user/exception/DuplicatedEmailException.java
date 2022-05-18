package com.yunhalee.withEmployee.user.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.InvalidValueException;

public class DuplicatedEmailException extends InvalidValueException {

    public DuplicatedEmailException(String message) {
        super(message);
    }

    public DuplicatedEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedEmailException(Throwable cause) {
        super(cause);
    }
}
