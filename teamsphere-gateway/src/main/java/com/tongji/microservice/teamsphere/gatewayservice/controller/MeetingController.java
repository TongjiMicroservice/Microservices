package com.tongji.microservice.teamsphere.gatewayservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingListResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.ParticipantListResponse;
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
    public MeetingResponse createMeeting(@RequestParam("projectId") int projectId,
                                         @RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                         @RequestParam("deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline) {
        if (!StpUtil.isLogin()){
            // 用户未登录
            return new MeetingResponse(APIResponse.notLoggedIn(), null);
        }
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
        if (!StpUtil.isLogin()){
            // 用户未登录
            return APIResponse.notLoggedIn();
        }
        return meetingService.cancelMeeting(meetingId);
    }

    @GetMapping("/meeting/project/{projectId}")
    @Operation(summary = "获取项目的会议列表", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingListResponse.class))),
            @ApiResponse(responseCode = "400", description = "获取会议列表失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingListResponse.class))),
    })
    public MeetingListResponse getMeetingsForProject(@PathVariable("projectId") int projectId) {
        if (!StpUtil.isLogin()){
            // 用户未登录
            return new MeetingListResponse(APIResponse.notLoggedIn(), null);
        }
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
        if (!StpUtil.isLogin()){
            // 用户未登录
            return new MeetingListResponse(APIResponse.notLoggedIn(), null);
        }
        return meetingService.getMeetingsForUser(userId);
    }

    @PostMapping("/meeting/participant")
    @Operation(summary = "添加参会人", responses = {
            @ApiResponse(responseCode = "200", description = "成功添加参会人",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "401", description = "添加参会人失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    public APIResponse addParticipant(
            @RequestParam("meetingId") String meetingId,
            @RequestParam("participantId") int participantId,
            @RequestParam("role") String role
    ) {
        if (!StpUtil.isLogin()){
            // 用户未登录
            return APIResponse.notLoggedIn();
        }
        return meetingService.addParticipant(meetingId, participantId, role);
    }

    @DeleteMapping("/meeting/participant/{participantId}")
    @Operation(summary = "移除参会人", responses = {
            @ApiResponse(responseCode = "200", description = "成功移除参会人",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "参会人不存在",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "401", description = "参会人移除失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    public APIResponse removeParticipant(
            @RequestParam("meetingId") String meetingId,
            @PathVariable("participantId") int participantId) {
        if (!StpUtil.isLogin()){
            // 用户未登录
            return APIResponse.notLoggedIn();
        }
        return meetingService.removeParticipant(meetingId, participantId);
    }

    @PutMapping("/meeting/participant/{participantId}/role")
    @Operation(summary = "修改参会人角色", responses = {
            @ApiResponse(responseCode = "200", description = "成功修改参会人角色",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "参会人不存在",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "401", description = "参会人角色修改失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    public APIResponse setParticipantRole(
            @RequestParam("meetingId") String meetingId,
            @PathVariable("participantId") int participantId,
            @RequestParam("role") String role
    ) {
        if (!StpUtil.isLogin()){
            // 用户未登录
            return APIResponse.notLoggedIn();
        }
        return meetingService.setParticipantRole(meetingId, participantId, role);
    }

    @Operation(summary = "获取会议参与者列表", responses = {
            @ApiResponse(responseCode = "200", description = "成功获取参与者列表",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantListResponse.class))),
            @ApiResponse(responseCode = "401", description = "会议不存在",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    @GetMapping("/meeting/{meetingId}/participants")
    public ParticipantListResponse getParticipantsForMeeting(
            @PathVariable("meetingId") String meetingId
    ) {
        if (!StpUtil.isLogin()) {
            // 用户未登录
            return new ParticipantListResponse(APIResponse.notLoggedIn(),null);
        }
        return meetingService.getParticipantsForMeeting(meetingId);
    }
}