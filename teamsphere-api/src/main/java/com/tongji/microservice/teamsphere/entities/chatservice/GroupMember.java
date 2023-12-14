package com.tongji.microservice.teamsphere.entities.chatservice;

import lombok.Data;

@Data
public class GroupMember {
    private Long id;

    private Long groupId;
    private Long memberId;
}
