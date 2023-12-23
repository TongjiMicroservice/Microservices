package com.tongji.microservice.teamsphere.entities.projectservice;

import lombok.Data;

@Data
public class Member {
    private int id;
    private int projectId;
    private Long memberId;
    private String role;
}
