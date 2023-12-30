package com.tongji.microservice.teamsphere.dto.meetingservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MeetingListResponse extends APIResponse implements Serializable  {
    List<MeetingData> meetings;

    public MeetingListResponse(APIResponse apiResponse, List<MeetingData> meetings) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.meetings = meetings;
    }
}