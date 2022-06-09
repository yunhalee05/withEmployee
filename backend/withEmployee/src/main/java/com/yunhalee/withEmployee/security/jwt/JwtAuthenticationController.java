package com.yunhalee.withEmployee.security.jwt;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;

    private JwtUserDetailsService userDetailsService;

    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationController(
        AuthenticationManager authenticationManager,
        JwtUserDetailsService userDetailsService,
        JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/api/authenticate")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        userDetailsService.loadUserByUsername(request.getUsername());
        return ResponseEntity.ok(jwtTokenUtil.generateToken(request.getUsername()));
    }

    private void authenticate(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new DisabledException("User disabled", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Password", e);
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<UserTokenResponse> login(@RequestBody JwtRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(userDetailsService.login(request.getUsername()));
    }

    @GetMapping("/api/login")
    public ResponseEntity<UserTokenResponse> loginWithToken(@Param("token") String token) {
        return ResponseEntity.ok(userDetailsService.loginWithToken(token));
    }

}
