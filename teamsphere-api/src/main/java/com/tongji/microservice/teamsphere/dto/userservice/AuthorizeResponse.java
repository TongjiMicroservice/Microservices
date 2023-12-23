package com.tongji.microservice.teamsphere.dto.userservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorizeResponse extends APIResponse implements Serializable {
    private int userid;

    public AuthorizeResponse(APIResponse apiResponse, int userid) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.userid = userid;
    }

    public AuthorizeResponse(APIResponse apiResponse) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.userid = 0;
    }
}
