package com.tongji.microservice.teamsphere.dto.userservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class UserQueryRequest {
    private String token; // 用户鉴权token
    private int userid;
    private String username,email;

}
