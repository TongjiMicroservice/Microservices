package com.tongji.microservice.teamsphere.dto.projectservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
