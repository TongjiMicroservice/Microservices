package com.tongji.microservice.teamsphere.dto.meetingservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class MeetingResponse extends APIResponse implements Serializable {
    MeetingData meetingData;

    public MeetingResponse(APIResponse apiResponse, MeetingData meetingData) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.meetingData = meetingData;
    }
}
