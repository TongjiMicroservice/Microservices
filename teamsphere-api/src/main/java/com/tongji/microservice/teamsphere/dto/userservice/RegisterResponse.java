package com.tongji.microservice.teamsphere.dto.userservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterResponse extends APIResponse implements Serializable {
    private UserData data;

    public RegisterResponse(APIResponse apiResponse, int id, String username) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.data = new UserData(id, username);
    }
}

