package com.yunhalee.withEmployee.company.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.InvalidValueException;

public class CompanyNameAlreadyInUseException extends InvalidValueException {

    public CompanyNameAlreadyInUseException(String message) {
        super(message);
    }

    public CompanyNameAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyNameAlreadyInUseException(Throwable cause) {
        super(cause);
    }
}
