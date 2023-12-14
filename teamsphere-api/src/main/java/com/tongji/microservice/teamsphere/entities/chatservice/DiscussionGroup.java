package com.tongji.microservice.teamsphere.entities.chatservice;

import lombok.Data;

@Data
public class DiscussionGroup {
    private Long groupId;
    private String projectId;
    private String groupName;
}
