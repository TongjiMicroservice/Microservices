package com.tongji.microservice.teamsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse implements Serializable {
    private int code;
    private String message;

    public APIResponse(APIResponse success) {
        this.code = success.code;
        this.message = success.message;
    }

    public static APIResponse success() {
        return new APIResponse(200, "success");
    }

    public static APIResponse fail(String msg) {
        return new APIResponse(400, msg);
    }
    public static APIResponse fakeToken() {
        return new APIResponse(403, "无效身份");
    }
    public static APIResponse unauthorized() {
        return new APIResponse(401, "无权操作");
    }public static APIResponse notLoggedIn() {
        return new APIResponse(401, "未登录");
    }

}
