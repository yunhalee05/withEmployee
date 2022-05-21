package com.yunhalee.withEmployee.team.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.InvalidValueException;

public class TeamNameAlreadyExistException extends InvalidValueException {

    public TeamNameAlreadyExistException(String message) {
        super(message);
    }

    public TeamNameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamNameAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
