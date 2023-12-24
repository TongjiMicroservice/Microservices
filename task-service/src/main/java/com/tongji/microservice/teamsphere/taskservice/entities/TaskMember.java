package com.tongji.microservice.teamsphere.taskservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tongji.microservice.teamsphere.entities.taskservice.TaskMemberData;
import lombok.AllArgsConstructor;
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

    public TaskMember(TaskMemberData member) {
        this.userId = member.getId();
        this.taskId = member.getTaskId();
        this.score = member.getScore();
        this.finishTime = member.getFinishTime();
        this.fileURL = member.getFileURL();
    }

}
