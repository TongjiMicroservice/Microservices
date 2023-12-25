package com.tongji.microservice.teamsphere.dto.projectservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrivilegeResponse extends APIResponse implements Serializable {
    private int privilege;
    public PrivilegeResponse(APIResponse response){
        super(response);
        this.privilege = 0;
    }
    public PrivilegeResponse(int privilege){
        super(success());
        this.privilege = privilege;
    }
}
