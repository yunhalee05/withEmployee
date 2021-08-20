package com.yunhalee.withEmployee.security;

import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email);

        if(user != null) return new JwtUserDetails(user);

        throw new UsernameNotFoundException("Could not find user with email : " + email);
    }
}
