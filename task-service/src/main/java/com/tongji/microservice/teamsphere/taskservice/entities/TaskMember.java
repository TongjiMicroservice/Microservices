package com.tongji.microservice.teamsphere.taskservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("TaskMember")
@AllArgsConstructor
public class TaskMember {
    @TableField("user_id")
    private int userId;
    @TableField("task_id")
    private int taskId;
    @TableField("score")
    private int score;
}
