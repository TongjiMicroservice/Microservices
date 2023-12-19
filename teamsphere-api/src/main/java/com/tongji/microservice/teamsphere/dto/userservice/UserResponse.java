package com.tongji.microservice.teamsphere.dto.userservice;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserResponse implements Serializable {
    private String username,password,email,avatar;
    public UserResponse(String username, String password, String email, String avatar) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
    }
}