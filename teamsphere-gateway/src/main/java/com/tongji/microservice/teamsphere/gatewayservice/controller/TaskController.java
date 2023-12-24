package com.tongji.microservice.teamsphere.gatewayservice.controller;


import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.ProjectTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskResponse;
import com.tongji.microservice.teamsphere.dto.userservice.UserResponse;
import com.tongji.microservice.teamsphere.dubbo.api.TaskService;
import com.tongji.microservice.teamsphere.entities.taskservice.TaskData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name= "TaskController", description = "任务管理微服务接口")
public class TaskController {
    @DubboReference(check = false)
    private TaskService taskService;
    @PostMapping("task")
    @Operation(summary = "创建任务", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse createTask(String token, int projectId, TaskData taskData){
        return taskService.createTask(token, projectId, taskData);
    }
    @DeleteMapping("task")
    @Operation(summary = "删除任务", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse deleteTask(String token, int taskId){
        return taskService.deleteTask(token, taskId);
    }

    @PostMapping("task/member")
    @Operation(summary = "添加任务成员", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse addTaskMember(String token, int taskId, int memberId){
        return taskService.addTaskMember(token, taskId, memberId);
    }
    @DeleteMapping("task/member")
    @Operation(summary = "删除任务成员", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse deleteTaskMember(String token, int taskId, int memberId){
        return taskService.deleteTaskMember(token, taskId, memberId);
    }

    @PatchMapping("task/member")
    @Operation(summary = "为成员评分", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse scoreTaskMember(String token, int taskId, int memberId, int score){
        return taskService.scoreTaskMember(token, taskId, memberId, score);
    }
    @PutMapping("task/member")
    @Operation(summary = "上传任务文件资料", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse uploadTaskFile(String token, int taskId, int memberId, String fileURL){
        return taskService.uploadTaskFile(token, taskId, memberId, fileURL);
    }
    @PatchMapping("task")
    @Operation(summary = "修改任务信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    APIResponse updateTaskInfo(String token, int taskId, TaskData taskData){
        return taskService.updateTaskInfo(token, taskId, taskData);
    }

    @GetMapping("task")
    @Operation(summary = "获取任务信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    TaskResponse getTaskInfo(String token, int taskId){
        return taskService.getTaskInfo(token, taskId);
    }
    @GetMapping("task/member")
    @Operation(summary = "获取任务成员清单", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    TaskMemberResponse getTaskMember(String token, int taskId){
        return taskService.getTaskMember(token, taskId);
    }
    @GetMapping("project/task")
    @Operation(summary = "获取项目任务清单", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    ProjectTaskResponse getTasksForProject(String token, int projectId){
        return taskService.getTasksForProject(token, projectId);
    }
}
