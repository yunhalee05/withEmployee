package com.yunhalee.withEmployee.security;

import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.entity.User;
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

        User entityUser = userRepo.findByEmail(username);

        if(entityUser != null) return new JwtUserDetails(entityUser);

        throw new UsernameNotFoundException("Could not find user with email : " + username);
    }
}
