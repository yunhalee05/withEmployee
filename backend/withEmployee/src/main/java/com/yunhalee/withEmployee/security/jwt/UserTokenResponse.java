package com.yunhalee.withEmployee.security.jwt;

import com.yunhalee.withEmployee.user.dto.UserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserTokenResponse {

    private UserResponse user;
    private String token;

    private UserTokenResponse(UserResponse user, String token) {
        this.user = user;
        this.token = token;
    }

    public static UserTokenResponse of(UserResponse user, String token){
        return new UserTokenResponse(user, token);
    }
}
