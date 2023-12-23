package com.tongji.microservice.teamsphere.dto.userservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.entities.userservice.UserData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterResponse extends APIResponse implements Serializable {
    private UserData data;
    private String token;
    public RegisterResponse(APIResponse apiResponse, int id, String username, String token) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.data = new UserData(id, username);
        this.token = token;
    }
    public RegisterResponse(APIResponse apiResponse){
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.data = null;
        this.token = null;
    }
}

