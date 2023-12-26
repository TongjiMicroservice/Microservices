package com.tongji.microservice.teamsphere.taskservice.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.CreateTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.ProjectTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskResponse;
import com.tongji.microservice.teamsphere.dubbo.api.TaskService;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskData;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberData;
import com.tongji.microservice.teamsphere.taskservice.entities.Task;
import com.tongji.microservice.teamsphere.taskservice.mapper.TaskMapper;
import com.tongji.microservice.teamsphere.taskservice.mapper.TaskMemberMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.tongji.microservice.teamsphere.dto.APIResponse.*;

@DubboService
public class TaskServiceImpl implements TaskService {

    // 注入TaskMapper和TaskMemberMapper
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskMemberMapper memberMapper;
    @Override
    public CreateTaskResponse createTask(String name, String description, int projectId, LocalDateTime deadline, int leader, int priority) {
        var flat = taskMapper.insert(
                new Task(name,description,projectId,deadline,leader,priority)
        );
        if(flat == 0)
            return new CreateTaskResponse(APIResponse.fail("创建失败")) ;
        int taskId = taskMapper.getTaskId(projectId,name);
        return new CreateTaskResponse(APIResponse.success(),taskId);
    }

    @Override
    public APIResponse deleteTask(int taskId) {
        int projectId = taskMapper.getProjectId(taskId);
        var flat = taskMapper.deleteTaskById(taskId);
        if(flat == 0)
            return fail("删除失败");
        return success();
    }

    @Override
    public APIResponse addTaskMember(int taskId, int memberId) {
        int projectId = taskMapper.getProjectId(taskId);
        var flat =  memberMapper.addMember(taskId,memberId);
        if(flat == 0)
            return fail("添加失败");
        return success();
    }

    @Override
    public APIResponse deleteTaskMember(int taskId, int memberId) {
        int projectId = taskMapper.getProjectId(taskId);
        var flat =  memberMapper.deleteMember(taskId,memberId);
        if(flat == 0)
            return fail("删除失败");
        return success();
    }

    @Override
    public APIResponse scoreTaskMember(int taskId, int memberId, int score) {
        int projectId = taskMapper.getProjectId(taskId);
        var flat =  memberMapper.setScore(taskId,memberId,score);
        if(flat == 0)
            return fail("评分失败");
        return success();
    }

    @Override
    public APIResponse uploadTaskFile(int taskId, int memberId, String fileURL) {
        int flat = memberMapper.setFileURL(taskId,memberId,fileURL, LocalDateTime.now());
        if(flat == 0)
            return fail("上传失败");
        return success();
    }

    @Override
    public APIResponse updateTaskInfo( int taskId, TaskData taskData) {
        UpdateWrapper <Task> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",taskId);
        int flat = taskMapper.update(new Task(taskData), updateWrapper);
        if(flat == 0)
            return fail("更新失败");
        return success();
    }

    @Override
    public TaskResponse getTaskInfo(int taskId) {
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
    public TaskMemberResponse getTaskMember(int taskId) {
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
    public ProjectTaskResponse getTasksForProject(int projectId) {
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
        System.out.println(list);
        return new ProjectTaskResponse(list);
    }

    @Override
    public ProjectTaskResponse getTasksForLeader(int userId) {
        List<TaskData> taskData = new ArrayList<>();
        var list = taskMapper.getTaskByLeader(userId);
        for(var task : list){
            taskData.add(new TaskData(
                    task.getId(),
                    task.getProjectId(),
                    task.getLeader(),
                    task.getName(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getStatus()
            ));
        }
        return new ProjectTaskResponse(taskData);
    }

    @Override
    public ProjectTaskResponse getTasksForMember(int userId) {
        List<TaskData> taskData= new ArrayList<>();
        var list = memberMapper.getTaskByUserId(userId);
        for(var i : list){
            Task task = taskMapper.getTaskById(list[i]);
            taskData.add(new TaskData(
                    task.getId(),
                    task.getProjectId(),
                    task.getLeader(),
                    task.getName(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getStatus()
            ));
        }
        return new ProjectTaskResponse(taskData);
    }
}
