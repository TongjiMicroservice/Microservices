package com.tongji.microservice.teamsphere.dto.meetingservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MeetingResponse extends APIResponse implements Serializable {
    String meetingUrl;

    public MeetingResponse(APIResponse apiResponse, String meetingUrl) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.meetingUrl = meetingUrl;
    }
}
