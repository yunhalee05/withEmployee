package com.yunhalee.withEmployee.security.jwt;

import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user != null) return new JwtUserDetails(user);
        throw new UsernameNotFoundException("Could not find user with email : " + username);
    }
}
