package com.tongji.microservice.teamsphere.dubbo.api;


import java.util.List;

public interface ChatService {
    void createDiscussionGroup(String projectId, String groupName, List<String> memberIds);
    void addMemberToGroup(String groupId, String memberId);

    void updateDiscussionGroup();
    void removeMemberFromGroup(String groupId, String memberId);

    //test
}
