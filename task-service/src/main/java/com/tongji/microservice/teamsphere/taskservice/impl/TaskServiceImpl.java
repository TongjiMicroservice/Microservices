package com.tongji.microservice.teamsphere.taskservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.CreateTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.ProjectTaskResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskResponse;
import com.tongji.microservice.teamsphere.dubbo.api.FileService;
import com.tongji.microservice.teamsphere.dubbo.api.TaskService;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskData;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberData;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.taskservice.entities.Task;
import com.tongji.microservice.teamsphere.taskservice.entities.TaskMember;
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

    // 注入TaskMapper和TaskMemberMapper
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskMemberMapper memberMapper;
    @DubboReference(check = false)
    private UserService userService;
    @DubboReference(check = false)
    private FileService fileService;
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
    public APIResponse judgeTask(int taskId,int status) {
        try {
            var flat = taskMapper.setStatus(taskId,status);
            if(flat == 0)
                return fail("审批失败");
            return success();
        }catch (Exception e){
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @Override
    public APIResponse uploadTaskFile(int userId, int taskId, String fileURL) {
        try{
            if(!memberMapper.exists(new QueryWrapper<TaskMember>().eq("task_id",taskId).eq("user_id",userId)))
                return fail("你不是该任务成员");
            int flat = taskMapper.setFileURL(taskId,fileURL,LocalDateTime.now());
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
            return new TaskResponse(TaskToTaskData(task));
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
            List<TaskMember> list = memberMapper.getMembersByTaskId(taskId);
            List<TaskMemberData> l = new ArrayList<>();
            for(var i : list){
                l.add(new TaskMemberData(
                        i.getUserId(),
                        i.getTaskId(),
                        i.getScore()
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
            for(var task : tasks){
                list.add(TaskToTaskData(task));
            }
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
            List<TaskData> taskDataList = new ArrayList<>();
            var list = taskMapper.getTaskByLeader(userId);
            for(var task : list)
                taskDataList.add(TaskToTaskData(task));
            return new ProjectTaskResponse(taskDataList);
        }catch (Exception e){
            e.printStackTrace();
            return new ProjectTaskResponse(fail(e.getMessage()));
        }
    }

    @Override
    public ProjectTaskResponse getTasksForMember(int userId) {
        try{
            List<TaskData> taskDataList= new ArrayList<>();
            var list = memberMapper.getTaskByUserId(userId);
            for(var i : list){
                Task task = taskMapper.getTaskById(i);
                taskDataList.add(TaskToTaskData(task));
            }
            return new ProjectTaskResponse(taskDataList);
        }catch (Exception e){
            e.printStackTrace();
            return new ProjectTaskResponse(fail(e.getMessage()));
        }
    }
    @Override
    public int getLeader(int taskId){
        return taskMapper.getLeader(taskId);
    }
    private TaskData TaskToTaskData(Task task){
        TaskData taskData = new TaskData();
        taskData.setId(task.getId());
        taskData.setProjectId(task.getProjectId());
        taskData.setLeader(task.getLeader());
        taskData.setName(task.getName());
        taskData.setDescription(task.getDescription());
        taskData.setDeadline(task.getDeadline());
        taskData.setStatus(task.getStatus());
        taskData.setPriority(task.getPriority());
        taskData.setFile(task.getFile());
        taskData.setFinishTime(task.getFinishTime());
        taskData.setLeaderName(userService.getUserInfo(task.getLeader()).getUsername());
        var f = fileService.getFileByURL(task.getFile());
        if(f != null)
            taskData.setFileName(f.getName());
        else
            taskData.setFileName("");
        return taskData;
    }
}
