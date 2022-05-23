package com.yunhalee.withEmployee.security.jwt;

import com.yunhalee.withEmployee.common.exception.exceptions.AuthException;
import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import com.yunhalee.withEmployee.user.dto.UserCompanyResponse;
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

    public String generateToken(User user) {
        return jwtTokenUtil.generateToken(user.getEmail());
    }

    public UserTokenResponse loginWithToken(String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Could not find user with email : " + email));
        return userTokenResponse(user, token);
    }

    private UserTokenResponse userTokenResponse(User user, String token) {
        return UserTokenResponse.of(SimpleUserResponse.of(user), token);
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

    public LoginUser findMemberByToken(String token, boolean isMember, boolean isLeader, boolean isCeo, boolean isAdmin) {
        if (!jwtTokenUtil.isValidToken(token) && (!isMember && !isLeader && !isCeo && !isAdmin)) {
            return new LoginUser();
        }
        if (!jwtTokenUtil.isValidToken(token)) {
            throw new AuthException("This token is invalid.");
        }
        String email = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User does not exist with email : " + email));
        if ( (isAdmin && !isAdmin(user)) || (isCeo && !isCeoOrAdmin(user)) || (isLeader && !isLeaderOrCeoOrAdmin(user))) {
            throw new AuthException("User don't have authorization.");
        }
        return LoginUser.of(user);
    }

    private boolean isAdmin(User user) {
        return user.getRole().equals(Role.ADMIN.name());
    }

    private boolean isCeoOrAdmin(User user) {
        return user.getRole().equals(Role.CEO.name()) || isAdmin(user);
    }

    private boolean isLeaderOrCeoOrAdmin(User user) {
        return user.getRole().equals(Role.LEADER.name()) || isCeoOrAdmin(user);
    }
}
