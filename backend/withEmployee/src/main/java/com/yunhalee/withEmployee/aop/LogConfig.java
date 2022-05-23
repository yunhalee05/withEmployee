package com.yunhalee.withEmployee.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
public class LogConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLogTrace();
    }

}
