package com.tongji.microservice.teamsphere.entities.userservice;

import lombok.Data;

@Data
public class User {
    private Long userId;

    private String username;
    private String email;
}
