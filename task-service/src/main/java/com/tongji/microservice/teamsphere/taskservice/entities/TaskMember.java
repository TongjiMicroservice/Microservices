package com.tongji.microservice.teamsphere.taskservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("TaskMember")
public class TaskMember {
    @TableField("user_id")
    private int userId;
    @TableField("task_id")
    private int taskId;
    @TableField("score")
    private int score;
    @TableField("finish_time")
    private LocalDateTime finishTime;
    @TableField("file")
    private String fileURL;
}
