package com.tongji.microservice.teamsphere.gatewayservice.controller;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.*;
import com.tongji.microservice.teamsphere.dubbo.api.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Tag(name = "MeetingController", description = "会议微服务接口")
public class MeetingController {
    @DubboReference(check = false)
    private MeetingService meetingService;

    @PostMapping("/meeting")
    @Operation(summary = "创建会议", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingResponse.class))),
            @ApiResponse(responseCode = "400", description = "创建会议失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingResponse.class))),
    })
    public MeetingResponse createMeeting(@RequestParam("projectId") String projectId,
                                         @RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                         @RequestParam("deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline) {
        return meetingService.createMeeting(projectId, title, description, startTime, deadline);
    }

    @DeleteMapping("/meeting/{meetingId}")
    @Operation(summary = "取消会议", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "取消会议失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    public APIResponse cancelMeeting(@PathVariable("meetingId") String meetingId) {
        return meetingService.cancelMeeting(meetingId);
    }

    @GetMapping("/meeting/project/{projectId}")
    @Operation(summary = "获取项目的会议列表", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingListResponse.class))),
            @ApiResponse(responseCode = "400", description = "获取会议列表失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingListResponse.class))),
    })
    public MeetingListResponse getMeetingsForProject(@PathVariable("projectId") String projectId) {
        return meetingService.getMeetingsForProject(projectId);
    }

    @GetMapping("/meeting/user/{userId}")
    @Operation(summary = "获取用户参与的会议列表", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingListResponse.class))),
            @ApiResponse(responseCode = "400", description = "获取会议列表失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingListResponse.class))),
    })
    public MeetingListResponse getMeetingsForUser(@PathVariable("userId") int userId) {
        return meetingService.getMeetingsForUser(userId);
    }
}