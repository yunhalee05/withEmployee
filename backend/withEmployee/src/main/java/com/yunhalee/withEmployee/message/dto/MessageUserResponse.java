package com.yunhalee.withEmployee.message.dto;

import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageUserResponse {

    private Integer id;
    private String name;
    private String imageUrl;

    private MessageUserResponse(Integer id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static MessageUserResponse of(User user) {
        return new MessageUserResponse(user.getId(), user.getName(), user.getImageUrl());
    }
}
