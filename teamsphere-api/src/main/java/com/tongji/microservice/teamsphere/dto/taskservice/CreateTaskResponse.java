package com.tongji.microservice.teamsphere.dto.taskservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateTaskResponse extends APIResponse implements Serializable {
    private int taskId;

    public CreateTaskResponse(APIResponse apiResponse, int taskId) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.taskId = taskId;
    }

    public CreateTaskResponse(APIResponse apiResponse){
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.taskId=0;
    }

}
