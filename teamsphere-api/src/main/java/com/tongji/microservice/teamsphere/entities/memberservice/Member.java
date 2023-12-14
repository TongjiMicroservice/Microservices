package com.tongji.microservice.teamsphere.entities.memberservice;

import lombok.Data;

@Data
public class Member {
    private Long id;

    private Long projectId;
    private Long memberId;
    private String role;
}
