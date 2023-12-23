package com.tongji.microservice.teamsphere.dto.projectservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.entities.projectservice.ProjectData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectInfoResponse extends APIResponse implements Serializable {
    private ProjectData data;

    public ProjectInfoResponse(APIResponse apiResponse) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.data = null;
    }
}
