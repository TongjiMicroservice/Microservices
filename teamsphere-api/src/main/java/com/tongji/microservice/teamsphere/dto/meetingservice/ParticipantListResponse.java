package com.tongji.microservice.teamsphere.dto.meetingservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParticipantListResponse extends APIResponse implements Serializable {
    List<ParticipantData> participants;

    public ParticipantListResponse(APIResponse apiResponse, List<ParticipantData> participants) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.participants = participants;
    }
}
