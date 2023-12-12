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

    public static APIResponse success() {
        return new APIResponse(200, "success");
    }

    public static APIResponse failure(int code, String message) {
        return new APIResponse(code, message);
    }
}
