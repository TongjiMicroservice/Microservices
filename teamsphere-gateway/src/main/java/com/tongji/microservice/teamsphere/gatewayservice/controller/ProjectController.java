package com.tongji.microservice.teamsphere.gatewayservice.controller;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.MembersResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.ProjectInfoResponse;
import com.tongji.microservice.teamsphere.dto.userservice.LoginResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.entities.projectservice.ProjectData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@Tag(name= "UserController", description = "项目微服务接口")
public class ProjectController {
    @DubboReference(check = false)
    private UserService userService;
    @DubboReference(check = false)
    private ProjectService projectService;
    @PostMapping("/project")
    @Operation(summary = "创建项目接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse creatProject(String token, ProjectData projectData){
        return projectService.creatProject(token,projectData);
    }
    @PostMapping("/project/member")
    @Operation(summary = "创建项目接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse addProjectMember(String token, int projectId, int memberId){
        return projectService.addProjectMember(token,projectId,memberId);
    }

    @PatchMapping("/project")
    @Operation(summary = "创建项目接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse updateProjectInfo(String token, int projectId, ProjectData projectData){
        return projectService.updateProjectInfo(token,projectId,projectData);
    }
    @DeleteMapping("/project/member")
    @Operation(summary = "创建项目接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse removeProjectMember(String token, int projectId, int memberId){
        return projectService.removeProjectMember(token,projectId,memberId);
    }
    @PatchMapping("/project/member/privilege")
    @Operation(summary = "创建项目接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse updateProjectMemberPrivilege(String token, int projectId, int memberId, int privilege){
        return projectService.updateProjectMemberPrivilege(token,projectId,memberId,privilege);
    }
    @GetMapping("/project/member")
    @Operation(summary = "创建项目接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    MembersResponse getProjectMembers(String token, int projectId){
        return projectService.getProjectMembers(token,projectId);
    }
    @GetMapping("/project")
    @Operation(summary = "创建项目接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    ProjectInfoResponse getProjectInfo(String token, int projectId){
        return projectService.getProjectInfo(token,projectId);
    }
    @DeleteMapping("/project")
    @Operation(summary = "创建项目接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
    })
    APIResponse deleteProject(String token, int projectId){
        return projectService.deleteProject(token,projectId);
    }
}
