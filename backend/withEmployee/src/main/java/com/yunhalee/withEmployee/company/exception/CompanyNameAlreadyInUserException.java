package com.yunhalee.withEmployee.company.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.InvalidValueException;

public class CompanyNameAlreadyInUserException extends InvalidValueException {

    public CompanyNameAlreadyInUserException(String message) {
        super(message);
    }

    public CompanyNameAlreadyInUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyNameAlreadyInUserException(Throwable cause) {
        super(cause);
    }
}
