package com.tongji.microservice.teamsphere.gatewayservice.controller;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.GroupMemberResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.RecentChatResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ChatService;
import com.tongji.microservice.teamsphere.dubbo.api.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "ChatController", description = "聊天微服务接口")
public class ChatController {
    @DubboReference(check = false)
    private ChatService chatService;

    @GetMapping("/chat/api")
    @Operation(summary = "获取聊天ws服务器API", responses = {
            @ApiResponse(responseCode = "200", description = "调用成功",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "调用失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    APIResponse getPort(){
        return chatService.getPort();
    }

    @PostMapping("/chat/group")
    @Operation(summary = "获取聊天ws服务器API", responses = {
            @ApiResponse(responseCode = "200", description = "调用成功",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "调用失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    APIResponse createGroup(String groupName, String groupInfo, List<String> memberIds){
        return chatService.createGroup(groupName, groupInfo, memberIds);
    }
    @GetMapping("/chat/recent-chat")
    @Operation(summary = "获取最近聊天对象", responses = {
            @ApiResponse(responseCode = "200", description = "调用成功",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecentChatResponse.class))),
            @ApiResponse(responseCode = "400", description = "调用失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecentChatResponse.class)))
    })
    RecentChatResponse getRecentChat(int userId){
        return chatService.getRecentChat(userId);
    }

    @GetMapping("/chat/member")
    @Operation(summary = "获取特定聊天组成员", responses = {
            @ApiResponse(responseCode = "200", description = "调用成功",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupMemberResponse.class))),
            @ApiResponse(responseCode = "400", description = "调用失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupMemberResponse.class)))
    })
    GroupMemberResponse getGroupMember(int groupId) {
        return chatService.getGroupMember(groupId);
    }
}
