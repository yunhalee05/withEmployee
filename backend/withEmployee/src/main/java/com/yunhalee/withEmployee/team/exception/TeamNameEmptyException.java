package com.yunhalee.withEmployee.team.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.InvalidValueException;

public class TeamNameEmptyException extends InvalidValueException {

    public TeamNameEmptyException(String message) {
        super(message);
    }

    public TeamNameEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamNameEmptyException(Throwable cause) {
        super(cause);
    }
}
