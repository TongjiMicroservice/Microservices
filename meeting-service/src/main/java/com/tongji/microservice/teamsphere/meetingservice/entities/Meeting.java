package com.tongji.microservice.teamsphere.meetingservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("meeting")
public class Meeting {
    @TableField("id")
    public int id;

    @TableField("project_id")
    public String projectId;

    @TableField("title")
    public String title;

    @TableField("description")
    public String description;

    @TableField("start_time")
    public LocalDateTime startTime;

    @TableField("duration")
    public int duration;
}
