package com.yunhalee.withEmployee.team.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.EntityNotFoundException;

public class TeamNotFoundException extends EntityNotFoundException {

    public TeamNotFoundException(String message) {
        super(message);
    }

    public TeamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamNotFoundException(Throwable cause) {
        super(cause);
    }
}
