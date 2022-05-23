package com.yunhalee.withEmployee.aop;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Throwable e);
}
