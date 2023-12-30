package com.tongji.microservice.teamsphere.chatservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.GroupMemberResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ChatService;

import java.util.List;

import static com.tongji.microservice.teamsphere.dto.APIResponse.*;

public class ChatServiceImpl implements ChatService {
    @Override
    public APIResponse getPort() {
        return success();
    }

    @Override
    public APIResponse createGroup(String groupName, String groupInfo, List<String> memberIds) {
        return null;
    }

    @Override
    public APIResponse updateDiscussionGroup() {
        return null;
    }

    @Override
    public APIResponse addMemberToGroup(String groupId, String memberId) {
        return null;
    }

    @Override
    public APIResponse removeMemberFromGroup(String groupId, String memberId) {
        return null;
    }

    @Override
    public GroupMemberResponse getGroupMember(String groupId) {
        return null;
    }
}
