package com.tongji.microservice.teamsphere.dto.userservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends APIResponse implements Serializable {
    private int userid;
    private String token;

    public LoginResponse(APIResponse apiResponse, int userid, String token) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.userid = userid;
        this.token = token;
    }
    public LoginResponse(APIResponse apiResponse){
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.userid=0;
        this.token="";
    }
}

