package com.tongji.microservice.teamsphere.dto.projectservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;

import java.io.Serializable;
import java.util.List;

public class ProjectJoinRequestResponse extends APIResponse implements Serializable {
    List<RequestData> list;
    public ProjectJoinRequestResponse(APIResponse response){
        super(response);
        this.list = null;
    }
    public ProjectJoinRequestResponse(List<RequestData> list){
        super(success());
        this.list = list;
    }
}
