package com.tongji.microservice.teamsphere.taskservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.tongji.microservice.teamsphere.taskservice.entities.TaskMember;
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
        try {
            var flat = taskMapper.insert(
                    new Task(name, description, projectId, deadline, leader, priority)
            );
            if(flat == 0)
                return new CreateTaskResponse(APIResponse.fail("创建失败")) ;
            int taskId = taskMapper.getTaskId(projectId,name);
            return new CreateTaskResponse(APIResponse.success(),taskId);
        }catch (Exception e) {
            return new CreateTaskResponse(APIResponse.fail(e.getMessage()));
        }
    }

    @Override
    public APIResponse deleteTask(int taskId) {
        try{
            int projectId = taskMapper.getProjectId(taskId);
            var flat = taskMapper.deleteTaskById(taskId);
            if(flat == 0)
                return fail("删除失败");
            return success();
        }catch (Exception e){
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @Override
    public APIResponse addTaskMember(int taskId, int memberId) {
        int projectId = taskMapper.getProjectId(taskId);
        try {
            var flat = memberMapper.addMember(taskId,memberId);
            if(flat == 0)
                return fail("添加失败");
            return success();
        }catch (Exception e){
            e.printStackTrace();
            return fail(e.getMessage());
        }

    }

    @Override
    public APIResponse deleteTaskMember(int taskId, int memberId) {
        try {
            int projectId = taskMapper.getProjectId(taskId);
            var flat = memberMapper.deleteMember(taskId, memberId);
            if(flat == 0)
                return fail("删除失败");
            return success();
        }catch (Exception e){
            e.printStackTrace();
            return fail(e.getMessage());
        }

    }

    @Override
    public APIResponse scoreTaskMember(int taskId, int memberId, int score) {
        try {
            int projectId = taskMapper.getProjectId(taskId);
            var flat =  memberMapper.setScore(taskId,memberId,score);
            if(flat == 0)
                return fail("评分失败");
            return success();
        }catch (Exception e){
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @Override
    public APIResponse uploadTaskFile(int taskId, int memberId, String fileURL) {
        try{
            int flat = memberMapper.setFileURL(taskId,memberId,fileURL, LocalDateTime.now());
            if(flat == 0)
                return fail("上传失败");
            return success();
        }catch (Exception e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @Override
    public APIResponse updateTaskInfo( int taskId, TaskData taskData) {
        try{
            UpdateWrapper <Task> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",taskId);
            int flat = taskMapper.update(new Task(taskData), updateWrapper);
            if(flat == 0)
                return fail("更新失败");
            return success();
        }catch (Exception e){
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @Override
    public TaskResponse getTaskInfo(int taskId) {
        try{
            Task task = taskMapper.selectById(taskId);
            if(task == null)
                return new TaskResponse(fail("任务不存在"));
            return new TaskResponse(new TaskData(
                    task.getId(),
                    task.getProjectId(),
                    task.getLeader(),
                    task.getName(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getStatus()
            ));
        }catch (Exception e){
            e.printStackTrace();
            return new TaskResponse(fail(e.getMessage()));
        }
    }

    @Override
    public TaskMemberResponse getTaskMember(int taskId) {
        try{
            Task task = taskMapper.selectById(taskId);
            if(task == null)
                return  new TaskMemberResponse(fail("任务不存在"));
            QueryWrapper<TaskMember> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("task_id",taskId);
            List<TaskMember> list = memberMapper.selectList(queryWrapper);
            List<TaskMemberData> l = new ArrayList<>();
            for(var i : list){
                l.add(new TaskMemberData(
                        i.getUserId(),
                        i.getTaskId(),
                        i.getScore(),
                        i.getFinishTime(),
                        i.getFileURL()
                ));
            }
            return new TaskMemberResponse(l);
        }catch (Exception e){
            e.printStackTrace();
            return new TaskMemberResponse(fail(e.getMessage()));
        }
    }

    @Override
    public ProjectTaskResponse getTasksForProject(int projectId) {
        try{
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
        }catch (Exception e){
            e.printStackTrace();
            return new ProjectTaskResponse(fail(e.getMessage()));
        }
    }

    @Override
    public ProjectTaskResponse getTasksForLeader(int userId) {
        try{
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
        }catch (Exception e){
            e.printStackTrace();
            return new ProjectTaskResponse(fail(e.getMessage()));
        }
    }

    @Override
    public ProjectTaskResponse getTasksForMember(int userId) {
        try{
            List<TaskData> taskData= new ArrayList<>();
            var list = memberMapper.getTaskByUserId(userId);
            for(var i : list){
                Task task = taskMapper.getTaskById(i);
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
        }catch (Exception e){
            e.printStackTrace();
            return new ProjectTaskResponse(fail(e.getMessage()));
        }

    }
}
