package com.tongji.microservice.teamsphere.dto.userservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserResponse extends APIResponse {
    private int userId;
    private String username, email, avatar;

    public UserResponse(int userid, String username, String email, String avatar) {
        super(success());        // 设置用户信息
        this.userId = userid;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
    }

    public UserResponse(APIResponse response) {
        super(response.getCode(), response.getMessage());
    }
}