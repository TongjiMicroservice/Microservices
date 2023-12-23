package com.tongji.microservice.teamsphere.entities.projectservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectData {
    private int projectId;
    private int projectScale;
    private String projectName;
    private String projectDescription;
    private String projectLeader;


}
