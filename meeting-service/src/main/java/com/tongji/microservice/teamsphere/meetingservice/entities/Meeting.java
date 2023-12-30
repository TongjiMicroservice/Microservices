package com.tongji.microservice.teamsphere.meetingservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Duration;

@Data
@TableName("meeting")
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    @TableField("id")
    public String id;

    @TableField("title")
    public String title;

    @TableField("project_id")
    public int projectId;

    @TableField("start_time")
    public LocalDateTime startTime;

    @TableField("duration")
    public int duration;

    @TableField("description")
    public String description;

    @TableField("url")
    public String url;

    @TableField("book_id")
    public String bookId;

    public Meeting(String id, int projectId, String title, String description, LocalDateTime starTime,
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

    public Meeting(MeetingData meetingData) {
        this.id = meetingData.getId();
        this.projectId = meetingData.getProjectId();
        this.title = meetingData.getTitle();
        this.description = meetingData.getDescription();
        this.startTime = meetingData.getStartTime();
        this.duration = meetingData.getDuration();
        this.url = meetingData.getUrl();
        this.bookId = meetingData.getBookId();
    }
}
