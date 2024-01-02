package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingListResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.ParticipantListResponse;

import java.time.LocalDateTime;

public interface MeetingService {
    APIResponse cancelMeeting(String meetingId);
    MeetingListResponse getMeetingsForProject(int projectId);
    MeetingListResponse getMeetingsForUser(int userId);
    MeetingResponse createMeeting(int projectId, String title, String description, LocalDateTime starTime, LocalDateTime deadline);
    APIResponse addParticipant(String meetingId, int participantId, String role);
    APIResponse removeParticipant(String meetingId, int participantId);
    APIResponse setParticipantRole(String meetingId, int participantId, String role);
    ParticipantListResponse getParticipantsForMeeting(String meetingId);

}
