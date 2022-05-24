package com.yunhalee.withEmployee.security.jwt;

import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUser {

    private Integer id;
    private String email;
    private Role role;

    public LoginUser(Integer id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public static LoginUser of(User user) {
        return new LoginUser(user.getId(), user.getEmail(), Role.valueOf(user.getRole()));
    }


    public boolean isMemberLevel() {
        return isMember() || isLeaderLevel();
    }

    public boolean isLeaderLevel() {
        return isLeader() || isCeoLevel();
    }

    public boolean isCeoLevel() {
        return isCeo() || isAdmin();
    }

    public boolean isMember() {
        return this.role.equals(Role.MEMBER);
    }

    public boolean isLeader() {
        return this.role.equals(Role.LEADER);
    }

    public boolean isCeo() {
        return this.role.equals(Role.CEO);
    }

    public boolean isAdmin() {
        return this.role.equals(Role.ADMIN);
    }

    public boolean isLoginUser(Integer userId) {
        return this.id == userId;
    }
}
