package com.tongji.microservice.teamsphere.dubbo.api;


import com.tongji.microservice.teamsphere.dto.APIResponse;

import java.util.List;

public interface ChatService {

    APIResponse getAPI();
    void createDiscussionGroup(String projectId, String groupName, List<String> memberIds);
    void addMemberToGroup(String groupId, String memberId);

    void updateDiscussionGroup();
    void removeMemberFromGroup(String groupId, String memberId);

    //test
}
