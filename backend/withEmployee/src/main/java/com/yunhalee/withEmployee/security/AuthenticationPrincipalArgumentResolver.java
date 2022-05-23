package com.yunhalee.withEmployee.security;

import com.yunhalee.withEmployee.security.jwt.JwtRequestFilter;
import com.yunhalee.withEmployee.security.jwt.JwtUserDetailsService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private JwtUserDetailsService jwtUserDetailsService;

    public AuthenticationPrincipalArgumentResolver(JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
        ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest,
        WebDataBinderFactory webDataBinderFactory) throws Exception {
        String credentials = JwtRequestFilter.getToken(webRequest.getNativeRequest(HttpServletRequest.class));
        boolean isMember = parameter.getParameterAnnotation(AuthenticationPrincipal.class).isMember();
        boolean isLeader = parameter.getParameterAnnotation(AuthenticationPrincipal.class).isLeader();
        boolean isCeo = parameter.getParameterAnnotation(AuthenticationPrincipal.class).isCeo();
        boolean isAdmin = parameter.getParameterAnnotation(AuthenticationPrincipal.class).isAdmin();
        return jwtUserDetailsService.findMemberByToken(credentials, isMember, isLeader, isCeo, isAdmin);
    }
}
