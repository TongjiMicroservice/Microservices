package com.tongji.microservice.teamsphere.dto.projectservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class ProjectQueryResponse extends APIResponse implements Serializable {
    List<ProjectData> projectDataList;
    public ProjectQueryResponse(APIResponse response){
        super(response);
        this.projectDataList=null;
    }
    public ProjectQueryResponse(List<ProjectData> projectDataList){
        super(success());
        this.projectDataList=projectDataList;
    }

}
