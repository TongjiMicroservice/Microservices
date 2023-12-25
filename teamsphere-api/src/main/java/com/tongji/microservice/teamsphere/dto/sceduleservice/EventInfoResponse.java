package com.tongji.microservice.teamsphere.dto.sceduleservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventInfoResponse extends APIResponse implements Serializable {
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime deadline;
    private String description;
    private String title;
    private int priority;

    public EventInfoResponse(APIResponse apiResponse) {
        super(apiResponse.getCode(), apiResponse.getMessage());
    }

    public EventInfoResponse(APIResponse apiResponse,int UserId, LocalDateTime StartTime, LocalDateTime Deadline,
                             String Description,String Title,int Priority){
        super(apiResponse.getCode(),apiResponse.getMessage());
        userId = UserId;
        startTime = StartTime;
        deadline = Deadline;
        description = Description;
        title = Title;
        priority = Priority;
    }
}
