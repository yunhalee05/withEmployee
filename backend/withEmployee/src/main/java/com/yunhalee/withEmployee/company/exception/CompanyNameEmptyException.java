package com.yunhalee.withEmployee.company.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.InvalidValueException;

public class CompanyNameEmptyException extends InvalidValueException {

    public CompanyNameEmptyException(String message) {
        super(message);
    }

    public CompanyNameEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyNameEmptyException(Throwable cause) {
        super(cause);
    }
}
