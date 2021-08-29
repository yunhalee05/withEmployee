package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.UserDTO;
import com.yunhalee.withEmployee.entity.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MessageController {

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message){

    }

    @MessageMapping("/join")
    public void joinUser(@Payload String userId){
        System.out.println(userId);
    }
}
