package com.tongji.microservice.teamsphere.dto.taskservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskResponse extends APIResponse implements Serializable {
    private TaskData taskData;
    public TaskResponse(APIResponse response){
        super(response.getCode(), response.getMessage());
    }

    public TaskResponse(TaskData taskData) {
        super(success());
        this.taskData = taskData;
    }
}
