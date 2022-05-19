package com.yunhalee.withEmployee.security.jwt;

import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.dto.UserCompanyResponse;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import com.yunhalee.withEmployee.user.dto.UserTeamResponse;
import com.yunhalee.withEmployee.user.exception.UserNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Could not find user with email : " + username));
        return new JwtUserDetails(user);
    }

    public UserTokenResponse login(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("Could not find user with email : " + email));
        return getUserTokenResponse(user);
    }


    public UserTokenResponse getUserTokenResponse(User user) {
        String token = jwtTokenUtil.generateToken(user.getEmail());
        return userTokenResponse(user, token);
    }

    public UserTokenResponse loginWithToken(String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Could not find user with email : " + email));
        return userTokenResponse(user, token);
    }

    private UserTokenResponse userTokenResponse(User user, String token) {
        return UserTokenResponse.of(UserResponse
                .of(user, userTeamResponses(user), userCompanyResponses(user)), token);
    }

    private List<UserTeamResponse> userTeamResponses(User user) {
        return user.getTeams().stream()
            .map(UserTeamResponse::of)
            .collect(Collectors.toList());
    }

    private List<UserCompanyResponse> userCompanyResponses(User user) {
        return user.getCompanies().stream()
            .map(UserCompanyResponse::of)
            .collect(Collectors.toList());
    }

}
