package com.yunhalee.withEmployee.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticationPrincipal {

    boolean isMember() default true;

    boolean isLeader() default false;

    boolean isCeo() default false;

    boolean isAdmin() default false;

}
