package com.tongji.microservice.teamsphere.meetingservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("meeting_participants")
public class MeetingParticipants{
    @TableField("id")
    public int id;
    @TableField("meeting_id")
    public String meetingId;
    @TableField("participant_id")
    public int participantId;
    @TableField("role")
    public String role;

    public MeetingParticipants(int id, String meetingId, int participantId, String role) {
        this.id = id;
        this.meetingId = meetingId;
        this.participantId = participantId;
        this.role = role;
    }
}
