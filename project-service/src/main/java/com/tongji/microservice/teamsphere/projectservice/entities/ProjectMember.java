package com.tongji.microservice.teamsphere.projectservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectMember {
    private int id;
    private int projectId;
    private int memberId;
    private String role;
}
