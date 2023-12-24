package com.tongji.microservice.teamsphere.taskservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("Task")
@NoArgsConstructor
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
    private int status;
    @TableField("leader")
    private int leader;

}
