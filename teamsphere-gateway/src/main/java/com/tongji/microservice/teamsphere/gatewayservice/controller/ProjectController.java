package com.tongji.microservice.teamsphere.gatewayservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.*;
import com.tongji.microservice.teamsphere.dto.userservice.LoginResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name= "ProjectController", description = "项目微服务接口")
public class ProjectController {
    @DubboReference(check = false)
    private ProjectService projectService;

    private boolean checkAdmin(int userId, int projectId){
        return projectService.getProjectMemberPrivilege(projectId,userId).getPrivilege() > 1;
    }

    @PostMapping("/project/create")
    @Operation(summary = "创建项目", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse creatProject(ProjectData projectData){
        if(!StpUtil.isLogin()){
            return APIResponse.notLoggedIn();
        }
        return projectService.creatProject(projectData);
    }
    @PostMapping("/project/member/add")
    @Operation(summary = "添加项目成员", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse addProjectMember(int projectId, int userId){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return projectService.addProjectMember(projectId,userId);
    }

    @PatchMapping("/project/info/update")
    @Operation(summary = "更新项目信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse updateProjectInfo(int projectId, ProjectData projectData){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return projectService.updateProjectInfo(projectId,projectData);
    }
    @DeleteMapping("/project/member/delete")
    @Operation(summary = "删除项目成员", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse removeProjectMember(int projectId, int userId){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return projectService.removeProjectMember(projectId,userId);
    }
    @PatchMapping("/project/privilege/update")
    @Operation(summary = "变更成员权限", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse updateProjectMemberPrivilege(int projectId, int userId, int privilege){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return projectService.updateProjectMemberPrivilege(projectId,userId,privilege);
    }
    @GetMapping("/project/member/get")
    @Operation(summary = "获取项目成员清单", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    MembersResponse getProjectMembers(int projectId){
        if (!StpUtil.isLogin()) {
            return new MembersResponse(APIResponse.notLoggedIn());
        }
        return projectService.getProjectMembers(projectId);
    }
    @GetMapping("/project/info/get")
    @Operation(summary = "获取项目信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    ProjectInfoResponse getProjectInfo(int projectId){
        return projectService.getProjectInfo(projectId);
    }
    @DeleteMapping("/project/delete")
    @Operation(summary = "删除项目", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse deleteProject(int projectId){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return projectService.deleteProject(projectId);
    }

    @GetMapping("/project/privilege/get")
    @Operation(summary = "获取用户项目权限", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    PrivilegeResponse getProjectMemberPrivilege(int projectId, int userId){
        return projectService.getProjectMemberPrivilege(projectId,userId);
    }

    @GetMapping("/project/privilege/get")
    @Operation(summary = "获取全部项目信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    ProjectQueryResponse queryProject(){
        if (!StpUtil.isLogin()) {
            return new ProjectQueryResponse(APIResponse.notLoggedIn());
        }
        return projectService.queryProject();
    }
}
