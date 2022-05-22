package com.yunhalee.withEmployee.user.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponses {

    private Long totalElement;
    private Integer totalPage;
    private List<UserResponse> users;

    private UserResponses(Long totalElement, Integer totalPage, List<UserResponse> users) {
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.users = users;
    }

    public static UserResponses of(Long totalElement, Integer totalPage, List<UserResponse> users) {
        return new UserResponses(totalElement, totalPage, users);
    }
}
