package com.tongji.microservice.teamsphere.dto.taskservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.entities.taskservice.TaskData;

import java.io.Serializable;
import java.util.List;

public class ProjectTaskResponse extends APIResponse implements Serializable {
    private List<TaskData> taskData;

    public ProjectTaskResponse(APIResponse response){
        super(response.getCode(), response.getMessage());
        this.taskData = null;
    }

    public ProjectTaskResponse(List<TaskData> taskData) {
        super(success());
        this.taskData = taskData;
    }
}
