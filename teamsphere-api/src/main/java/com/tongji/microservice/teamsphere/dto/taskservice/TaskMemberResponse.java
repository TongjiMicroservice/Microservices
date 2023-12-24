package com.tongji.microservice.teamsphere.dto.taskservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.entities.taskservice.TaskMember;

import java.io.Serializable;
import java.util.List;

public class TaskMemberResponse extends APIResponse implements Serializable {
    private List<TaskMember> taskMember;

    public TaskMemberResponse(APIResponse response){
        super(response.getCode(),response.getMessage());
        this.taskMember=null;
    }

    public TaskMemberResponse(List<TaskMember> taskMember){
        super(success());
        this.taskMember=taskMember;
    }
}
