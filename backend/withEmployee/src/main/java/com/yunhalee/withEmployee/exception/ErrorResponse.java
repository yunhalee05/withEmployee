package com.yunhalee.withEmployee.exception;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
    private long timestamp;
}
