package com.tongji.microservice.teamsphere.dubbo.api;


import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.GroupMemberResponse;

import java.util.List;

public interface ChatService {

    APIResponse getPort();
    APIResponse createGroup(String groupName, String groupInfo, List<String> memberIds);

    APIResponse updateDiscussionGroup();
    APIResponse addMemberToGroup(String groupId, String memberId);
    APIResponse removeMemberFromGroup(String groupId, String memberId);

    GroupMemberResponse getGroupMember(String groupId);
    //test
}