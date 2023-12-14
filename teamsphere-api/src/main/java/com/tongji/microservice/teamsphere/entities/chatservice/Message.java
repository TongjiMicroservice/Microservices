package com.tongji.microservice.teamsphere.entities.chatservice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Long messageId;

    private Long groupId;
    private Long senderId;
    private String messageText;
    private LocalDateTime timestamp;
}
