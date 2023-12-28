package com.tongji.microservice.teamsphere.meetingservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Duration;

@Data
@TableName("meeting")
public class Meeting {
    @TableField("id")
    public String id;

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

    @TableField("book_id")
    public String bookId;

    @TableField("url")
    public String url;

    public Meeting(String id, String projectId, String title, String description, LocalDateTime starTime,
                   LocalDateTime endTime, String meetingUrl, String bookId) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.startTime = starTime;
        this.duration = (int)Duration.between(starTime, endTime).getSeconds();
        this.url = meetingUrl;
        this.bookId = bookId;
    }
}
