package com.tongji.microservice.teamsphere.meetingservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("meeting_participants")
@NoArgsConstructor
@AllArgsConstructor
public class MeetingParticipants{
    @TableField("id")
    public int id;
    @TableField("meeting_id")
    public String meetingId;
    @TableField("participant_id")
    public int participantId;
    @TableField("role")
    public String role;

    public MeetingParticipants(String meetingId, int participantId, String role){
        this.meetingId = meetingId;
        this.participantId = participantId;
        this.role = role;
    }
}
