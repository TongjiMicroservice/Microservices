package com.tongji.microservice.teamsphere.gatewayservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.*;
import com.tongji.microservice.teamsphere.dto.userservice.LoginResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "ProjectController", description = "项目微服务接口")
public class ProjectController {
    @DubboReference(check = false)
    private ProjectService projectService;
    @DubboReference(check = false)
    private UserService userService;

    private boolean checkAdmin(int userId, int projectId) {
        return projectService.getProjectMemberPrivilege(projectId, userId).getPrivilege() > 1;
    }

    @PostMapping("/project/create")
    @Operation(summary = "创建项目", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectIdResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectIdResponse.class))),
    })
    ProjectIdResponse creatProject(ProjectData projectData) {
        if (!StpUtil.isLogin()) {
            return new ProjectIdResponse(APIResponse.notLoggedIn(), -1);
        }
        return projectService.creatProject(projectData);
    }

    @PostMapping("/project/members")
    @Operation(summary = "添加项目成员", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    APIResponse addProjectMember(int projectId, int userId) {
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(), projectId)) {
            return APIResponse.fail("没有权限");
        }
        return projectService.addProjectMember(projectId, userId);
    }

    @PostMapping("/project/member/add-by-email")
    @Operation(summary = "通过email添加项目成员", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    APIResponse addProjectMemberByEmail(int projectId, String email) {
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(), projectId)) {
            return APIResponse.fail("没有权限");
        }
        int userId = userService.getUserInfoByEmail(email).getUserId();
        return projectService.addProjectMember(projectId, userId);
    }

    @PatchMapping("/project/info")
    @Operation(summary = "更新项目信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    APIResponse updateProjectInfo(int projectId, ProjectData projectData) {
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(), projectId)) {
            return APIResponse.fail("没有权限");
        }
        return projectService.updateProjectInfo(projectId, projectData);
    }

    @DeleteMapping("/project/member")
    @Operation(summary = "删除项目成员", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    APIResponse removeProjectMember(int projectId, int userId) {
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(), projectId)) {
            return APIResponse.fail("没有权限");
        }
        return projectService.removeProjectMember(projectId, userId);
    }

    @PatchMapping("/project/privilege")
    @Operation(summary = "变更成员权限", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    APIResponse updateProjectMemberPrivilege(int projectId, int userId, int privilege) {
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(), projectId)) {
            return APIResponse.fail("没有权限");
        }
        return projectService.updateProjectMemberPrivilege(projectId, userId, privilege);
    }

    @GetMapping("/project/members")
    @Operation(summary = "获取项目成员清单", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MembersResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MembersResponse.class))),
    })
    MembersResponse getProjectMembers(int projectId) {
        if (!StpUtil.isLogin()) {
            return new MembersResponse(APIResponse.notLoggedIn());
        }
        return projectService.getProjectMembers(projectId);
    }

    @GetMapping("/project/info")
    @Operation(summary = "获取项目信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectInfoResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectInfoResponse.class))),
    })
    ProjectInfoResponse getProjectInfo(int projectId) {
        return projectService.getProjectInfo(projectId);
    }

    @DeleteMapping("/project")
    @Operation(summary = "删除项目", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    APIResponse deleteProject(int projectId) {
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(), projectId)) {
            return APIResponse.fail("没有权限");
        }
        return projectService.deleteProject(projectId);
    }

    @GetMapping("/project/privilege")
    @Operation(summary = "获取用户项目权限", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrivilegeResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrivilegeResponse.class))),
    })
    PrivilegeResponse getProjectMemberPrivilege(int projectId, int userId) {
        return projectService.getProjectMemberPrivilege(projectId, userId);
    }

    @GetMapping("/project/all")
    @Operation(summary = "获取全部项目信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
    })
    ProjectQueryResponse queryProject() {
        if (!StpUtil.isLogin()) {
            return new ProjectQueryResponse(APIResponse.notLoggedIn());
        }
        return projectService.queryProject();
    }

    @GetMapping("/project/project-by-user")
    @Operation(summary = "获取用户加入的项目信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
    })
    ProjectQueryResponse getProjectByUserId() {
        if (!StpUtil.isLogin()) {
            return new ProjectQueryResponse(APIResponse.notLoggedIn());
        }
        int userId = Integer.parseInt(StpUtil.getLoginId().toString());
        return projectService.getProjectByUserId(userId);
    }

    @PostMapping("project/request")
    @Operation(summary = "请求加入项目", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
    })
    APIResponse makeJoinRequest(int projectId) {
        if (!StpUtil.isLogin()) {
            return new ProjectQueryResponse(APIResponse.notLoggedIn());
        }
        int userId = Integer.parseInt(StpUtil.getLoginId().toString());
        return projectService.makeJoinRequest(userId, projectId);
    }
    @GetMapping("project/request")
    @Operation(summary = "查看请求加入项目的人", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
    })
    ProjectJoinRequestResponse getProjectJoinRequest(int projectId){
        if (!StpUtil.isLogin()) {
            return new ProjectJoinRequestResponse(APIResponse.notLoggedIn());
        }
        int userId = Integer.parseInt(StpUtil.getLoginId().toString());
        if(!checkAdmin(userId,projectId))
            return new ProjectJoinRequestResponse(APIResponse.fail("没有权限"));
        return projectService.getProjectJoinRequest(projectId);
    }

    @PatchMapping("project/request")
    @Operation(summary = "进行新加成员审核", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectQueryResponse.class))),
    })
    APIResponse judgeJoinRequest(int userId, int projectId, int judgement){
        if (!StpUtil.isLogin()) {
            return new ProjectJoinRequestResponse(APIResponse.notLoggedIn());
        }
        int adminId = Integer.parseInt(StpUtil.getLoginId().toString());
        if(!checkAdmin(adminId, projectId))
            return new ProjectJoinRequestResponse(APIResponse.fail("没有权限"));
        return projectService.judgeJoinRequest(userId,projectId,judgement);
    }
}
