package com.tongji.microservice.teamsphere.entities.meetingservice;

import lombok.Data;

@Data
public class MeetingParticipant {
    private Long id;

    private Long meetingId;
    private Long participantId;
}
