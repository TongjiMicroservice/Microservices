package com.tongji.microservice.teamsphere.taskservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("Task")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @TableField("id")
    private int id;
    @TableField("name")
    private String name;
    @TableField("description")
    private String description;
    @TableField("project_id")
    private int projectId;
    @TableField("deadline")
    private LocalDateTime deadline;
    @TableField("status")
    private int status;  //0正常 1已提交 2已审核
    @TableField("leader")
    private int leader;
    @TableField("priority")
    private int priority;  // 优先级: 0-低, 1-中, 2-高
    @TableField("file")
    private String file;
    @TableField("finish_time")
    private LocalDateTime finishTime;
    public Task(TaskData taskData) {
        this.projectId = taskData.getProjectId();
        this.name = taskData.getName();
        this.description = taskData.getDescription();
        this.id = taskData.getId();
        this.deadline = taskData.getDeadline();
        this.status = taskData.getStatus();
        this.leader = taskData.getLeader();
        this.priority = taskData.getPriority();
        this.file = taskData.getFile();
        this.finishTime = taskData.getFinishTime();
    }

    public Task(String name, String description, int projectId, LocalDateTime deadline, int leader, int priority) {
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.deadline = deadline;
        this.leader = leader;
        this.priority = priority;
        this.status = 0;
        //其他值均为空
    }
}
