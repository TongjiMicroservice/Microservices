package com.tongji.microservice.teamsphere.dto.projectservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectInfoResponse extends APIResponse implements Serializable {
    private int projectId;
    private ProjectData data;

    public ProjectInfoResponse(APIResponse apiResponse) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.projectId=0;
        this.data = null;
    }

    public ProjectInfoResponse(APIResponse success, int id, ProjectData projectData) {
        super(success.getCode(), success.getMessage());
        this.projectId = id;
        this.data = projectData;
    }
}
