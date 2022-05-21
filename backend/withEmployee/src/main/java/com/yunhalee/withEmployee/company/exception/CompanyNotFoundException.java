package com.yunhalee.withEmployee.company.exception;

import com.yunhalee.withEmployee.common.exception.exceptions.EntityNotFoundException;

public class CompanyNotFoundException extends EntityNotFoundException {

    public CompanyNotFoundException(String message) {
        super(message);
    }

    public CompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyNotFoundException(Throwable cause) {
        super(cause);
    }
}
