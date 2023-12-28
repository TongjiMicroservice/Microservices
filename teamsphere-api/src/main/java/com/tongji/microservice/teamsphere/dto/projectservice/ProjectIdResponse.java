package com.tongji.microservice.teamsphere.dto.projectservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectIdResponse extends APIResponse {
    private int projectId;

    public ProjectIdResponse(APIResponse apiResponse,int projectId) {
        super(apiResponse.getCode(),apiResponse.getMessage());
        this.projectId = projectId;
    }
}
