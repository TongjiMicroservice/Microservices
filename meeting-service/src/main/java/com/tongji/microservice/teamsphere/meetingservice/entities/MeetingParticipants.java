package com.tongji.microservice.teamsphere.meetingservice.entities;

public class MeetingParticipants{
    public int id;
    public int meetingId;
    public int participantId;
    public String role; // 0: host, 1: participant
}
