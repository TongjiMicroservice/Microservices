package com.tongji.microservice.teamsphere.dubbo.api;


import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.GroupMemberResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.RecentChatResponse;

import java.util.List;

public interface ChatService {

    APIResponse getPort();
    APIResponse createGroup(String groupName, String groupInfo, List<String> memberIds);

    RecentChatResponse getRecentChat(int userId);
    GroupMemberResponse getGroupMember(int groupId);
    //test
}