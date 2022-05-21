package com.yunhalee.withEmployee.team.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.InvalidValueException;

public class TeamNameAlreadyInUseException extends InvalidValueException {

    public TeamNameAlreadyInUseException(String message) {
        super(message);
    }

    public TeamNameAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamNameAlreadyInUseException(Throwable cause) {
        super(cause);
    }
}
