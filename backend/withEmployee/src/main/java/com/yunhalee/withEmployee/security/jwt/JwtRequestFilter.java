package com.yunhalee.withEmployee.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String username = "";
        String token = getToken(request);
        if (existsToken(token)) {
            username = getUsername(token);
        }
        validateToken(token, username, request);
        chain.doFilter(request, response);
    }

    public static String getToken(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            return requestTokenHeader.substring(7);
        }
        return "";
    }

    public String getUsername(String token) {
        String username = "";
        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            throw new JwtException("JWT Token has expired");
        }
        return username;
    }

    private boolean existsToken(String token) {
        return !token.isBlank() && !token.isEmpty();
    }

    private boolean existsUsername(String username) {
        return !username.isBlank() && !username.isEmpty();
    }

    private void validateToken(String token, String username, HttpServletRequest request) {
        if (existsUsername(username)
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            authenticate(token, userDetails, request);
        }
    }

    private void authenticate(String token, UserDetails userDetails, HttpServletRequest request) {
        if (jwtTokenUtil.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
        }
    }


}
