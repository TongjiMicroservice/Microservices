package com.tongji.microservice.teamsphere.dto.taskservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
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
