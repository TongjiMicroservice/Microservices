package com.tongji.microservice.teamsphere.taskservice.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.ProjectTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.dubbo.api.TaskService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.entities.taskservice.TaskData;
import com.tongji.microservice.teamsphere.entities.taskservice.TaskMemberData;
import com.tongji.microservice.teamsphere.taskservice.entities.Task;
import com.tongji.microservice.teamsphere.taskservice.mapper.TaskMapper;
import com.tongji.microservice.teamsphere.taskservice.mapper.TaskMemberMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.tongji.microservice.teamsphere.dto.APIResponse.*;

@DubboService
public class TaskServiceImpl implements TaskService {

    @DubboReference(check = false)
    private TaskService taskService;
    @DubboReference(check = false)
    private ProjectService projectService;
    @DubboReference(check = false)
    private UserService userService;
    // 注入TaskMapper和TaskMemberMapper
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskMemberMapper memberMapper;
    @Override
    public APIResponse createTask(String token, int projectId, TaskData taskData) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return fakeToken();
        }
        System.out.printf("projectId = %d,userId = %d\n",projectId,adminId);
        int privilege = projectService.getProjectMemberPrivilege(token,projectId,adminId).getPrivilege();
        if(privilege <= 1){
            return fail("没有权限");
        }
        var flat = taskMapper.insert(new Task(taskData));
        if(flat == 0)
            return fail("创建失败");
        return success();
    }

    @Override
    public APIResponse deleteTask(String token, int taskId) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return fakeToken();
        }
        int projectId = taskMapper.getProjectId(taskId);
        int privilege = projectService.getProjectMemberPrivilege(token,projectId,adminId).getPrivilege();
        if(privilege <= 1){
            return fail("没有权限");
        }
        var flat = taskMapper.deleteTaskById(taskId);
        if(flat == 0)
            return fail("删除失败");
        return success();
    }

    @Override
    public APIResponse addTaskMember(String token, int taskId, int memberId) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return fakeToken();
        }
        int projectId = taskMapper.getProjectId(taskId);
        int privilege = projectService.getProjectMemberPrivilege(token,projectId,adminId).getPrivilege();
        if(privilege <= 1){
            return fail("没有权限");
        }
        var flat =  memberMapper.addMember(taskId,memberId);
        if(flat == 0)
            return fail("添加失败");
        return success();
    }

    @Override
    public APIResponse deleteTaskMember(String token, int taskId, int memberId) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return fakeToken();
        }
        int projectId = taskMapper.getProjectId(taskId);
        int privilege = projectService.getProjectMemberPrivilege(token,projectId,adminId).getPrivilege();
        if(privilege <= 1){
            return fail("没有权限");
        }
        var flat =  memberMapper.deleteMember(taskId,memberId);
        if(flat == 0)
            return fail("删除失败");
        return success();
    }

    @Override
    public APIResponse scoreTaskMember(String token, int taskId, int memberId, int score) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return fakeToken();
        }
        int projectId = taskMapper.getProjectId(taskId);
        int privilege = projectService.getProjectMemberPrivilege(token,projectId,adminId).getPrivilege();
        if(privilege <= 1){
            return fail("没有权限");
        }
        var flat =  memberMapper.setScore(taskId,memberId,score);
        if(flat == 0)
            return fail("评分失败");
        return success();
    }

    @Override
    public APIResponse uploadTaskFile(String token, int taskId, int memberId, String fileURL) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0 || adminId != memberId){
            return fakeToken();
        }
        int flat = memberMapper.setFileURL(taskId,memberId,fileURL, LocalDateTime.now());
        if(flat == 0)
            return fail("上传失败");
        return success();
    }

    @Override
    public APIResponse updateTaskInfo(String token, int taskId, TaskData taskData) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return fakeToken();
        }
        int projectId = taskMapper.getProjectId(taskId);
        int privilege = projectService.getProjectMemberPrivilege(token,projectId,adminId).getPrivilege();
        if(privilege <= 1){
            return fail("没有权限");
        }
        UpdateWrapper <Task> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",taskId);
        int flat = taskMapper.update(new Task(taskData), updateWrapper);
        if(flat == 0)
            return fail("更新失败");
        return success();
    }

    @Override
    public TaskResponse getTaskInfo(String token, int taskId) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return new TaskResponse(fakeToken());
        }
        Task task = taskMapper.selectById(taskId);
        if(task == null)
            return  new TaskResponse(fail("任务不存在"));
        return new TaskResponse(new TaskData(
                task.getId(),
                task.getProjectId(),
                task.getLeader(),
                task.getName(),
                task.getDescription(),
                task.getDeadline(),
                task.getStatus()
        ));
    }

    @Override
    public TaskMemberResponse getTaskMember(String token, int taskId) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return new TaskMemberResponse(fakeToken());
        }
        Task task = taskMapper.selectById(taskId);
        if(task == null)
            return  new TaskMemberResponse(fail("任务不存在"));
        var l = memberMapper.getMembersByTaskId(taskId);
        List<TaskMemberData> list = new ArrayList<>();
        for (var member : l)
            list.add(new TaskMemberData(
                    member.getUserId(),
                    member.getTaskId(),
                    member.getScore(),
                    member.getFinishTime(),
                    member.getFileURL()
            ));
        return new TaskMemberResponse(list);
    }

    @Override
    public ProjectTaskResponse getTasksForProject(String token, int projectId) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return new ProjectTaskResponse(fakeToken());
        }
        List<Task> tasks = taskMapper.selectTaskByProjectId(projectId);
        List<TaskData> list = new ArrayList<>();
        for(var task : tasks)
            list.add(new TaskData(
                    task.getId(),
                    task.getProjectId(),
                    task.getLeader(),
                    task.getName(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getStatus()
            ));
        return new ProjectTaskResponse(list);
    }
}
