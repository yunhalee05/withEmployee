package com.yunhalee.withEmployee.security.jwt;

import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserTokenResponse {

    private SimpleUserResponse user;
    private String token;

    private UserTokenResponse(SimpleUserResponse user, String token) {
        this.user = user;
        this.token = token;
    }

    public static UserTokenResponse of(SimpleUserResponse user, String token){
        return new UserTokenResponse(user, token);
    }
}
