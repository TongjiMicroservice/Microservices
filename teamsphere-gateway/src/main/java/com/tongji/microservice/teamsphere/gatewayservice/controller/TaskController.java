package com.tongji.microservice.teamsphere.gatewayservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.CreateTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.ProjectTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskResponse;
import com.tongji.microservice.teamsphere.dto.userservice.UserResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.dubbo.api.TaskService;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Tag(name= "TaskController", description = "任务管理微服务接口")
public class TaskController {
    @DubboReference(check = false)
    private TaskService taskService;

    @DubboReference(check = false)
    private ProjectService projectService;

    private boolean checkAdmin(int userId, int projectId){
        return projectService.getProjectMemberPrivilege(projectId,userId).getPrivilege() > 1;
    }

    @PostMapping("/task/create")
    @Operation(summary = "创建任务", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = CreateTaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateTaskResponse.class))),
    })
    CreateTaskResponse createTask(int projectId, String name, String description, int leader, LocalDateTime deadline, int priority){
        if(!StpUtil.isLogin()){
            return new CreateTaskResponse(APIResponse.notLoggedIn());
        }
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return new CreateTaskResponse(APIResponse.fail("没有权限"));
        }
        return taskService.createTask(name, description, projectId, deadline, leader, priority);
    }
    @DeleteMapping("/task/delete")
    @Operation(summary = "删除任务", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    APIResponse deleteTask(int taskId){
        if(!StpUtil.isLogin()){
            return APIResponse.notLoggedIn();
        }
        int projectId = taskService.getTaskInfo(taskId).getTaskData().getProjectId();
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return taskService.deleteTask(taskId);
    }

    @PostMapping("/task/member/add")
    @Operation(summary = "添加任务成员", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    APIResponse addTaskMember(int taskId, int memberId){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        int projectId = taskService.getTaskInfo(taskId).getTaskData().getProjectId();
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return taskService.addTaskMember(taskId, memberId);
    }
    @DeleteMapping("/task/member/delete")
    @Operation(summary = "删除任务成员", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse deleteTaskMember(int taskId, int memberId){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        int projectId = taskService.getTaskInfo(taskId).getTaskData().getProjectId();
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return taskService.deleteTaskMember(taskId, memberId);
    }

    @PatchMapping("/task/member/score")
    @Operation(summary = "为成员评分", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse scoreTaskMember(int taskId, int memberId, int score){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        int projectId = taskService.getTaskInfo(taskId).getTaskData().getProjectId();
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return taskService.scoreTaskMember(taskId, memberId, score);
    }
    @PutMapping("/task/member/upload")
    @Operation(summary = "上传任务文件资料", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse uploadTaskFile(int taskId, int memberId, String fileURL){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        int projectId = taskService.getTaskInfo(taskId).getTaskData().getProjectId();
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return taskService.uploadTaskFile(taskId, memberId, fileURL);
    }
    @PatchMapping("/task/info/update")
    @Operation(summary = "修改任务信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse updateTaskInfo(int taskId, TaskData taskData){
        if (!StpUtil.isLogin()) {
            return APIResponse.notLoggedIn();
        }
        int projectId = taskService.getTaskInfo(taskId).getTaskData().getProjectId();
        if (!checkAdmin(StpUtil.getLoginIdAsInt(),projectId)){
            return APIResponse.fail("没有权限");
        }
        return taskService.updateTaskInfo(taskId, taskData);
    }

    @GetMapping("/task/info/get")
    @Operation(summary = "获取任务信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    TaskResponse getTaskInfo(int taskId){
        if (!StpUtil.isLogin()) {
            return new TaskResponse(APIResponse.notLoggedIn());
        }
        return taskService.getTaskInfo(taskId);
    }
    @GetMapping("/task/member/get")
    @Operation(summary = "获取任务成员清单", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    TaskMemberResponse getTaskMember(int taskId){
        if (!StpUtil.isLogin()) {
            return new TaskMemberResponse(APIResponse.notLoggedIn());
        }
        return taskService.getTaskMember(taskId);
    }
    @GetMapping("/task/list")
    @Operation(summary = "获取项目任务清单", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = ProjectTaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectTaskResponse.class))),
    })
    ProjectTaskResponse getTasksForProject(int projectId){
        if (!StpUtil.isLogin()) {
            return new ProjectTaskResponse(APIResponse.notLoggedIn());
        }
        var res= taskService.getTasksForProject( projectId);
        System.out.println(res);
        return res;
    }

}
