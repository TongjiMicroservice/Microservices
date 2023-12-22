package com.tongji.microservice.teamsphere.dto.projectservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectInfoResponse extends APIResponse implements Serializable {
    private int projectId;
    private String projectName;
    private String projectDescription;
    private LocalDateTime projectCreateTime;

    public ProjectInfoResponse(APIResponse apiResponse, int projectId, String projectName, String projectDescription, LocalDateTime projectCreateTime) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectCreateTime = projectCreateTime;
    }
}
