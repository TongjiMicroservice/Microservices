package com.tongji.microservice.teamsphere.dto.taskservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskMemberResponse extends APIResponse implements Serializable {
    private List<TaskMemberData> taskMember;

    public TaskMemberResponse(APIResponse response){
        super(response.getCode(),response.getMessage());
        this.taskMember=null;
    }

    public TaskMemberResponse(List<TaskMemberData> taskMember){
        super(success());
        this.taskMember=taskMember;
    }
}
