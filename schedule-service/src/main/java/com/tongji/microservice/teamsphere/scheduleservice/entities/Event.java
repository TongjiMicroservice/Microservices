package com.tongji.microservice.teamsphere.scheduleservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("event")
public class Event {
    @TableField("id")
    public int id;
    @TableField("user_id")
    public int userId;
    @TableField("start_time")
    public LocalDateTime startTime;
    @TableField("deadline")
    public LocalDateTime deadline;
    @TableField("description")
    public String description;
    @TableField("title")
    public String title;

    @TableField("priority")
    public int priority;  // 0: low, 1: medium, 2: high
}
