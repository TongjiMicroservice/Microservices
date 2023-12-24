package com.tongji.microservice.teamsphere.dto.sceduleservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventsResponse extends APIResponse implements Serializable{
    private List<EventData> events;

    public EventsResponse(APIResponse apiResponse, List<EventData> events) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.events = events;
    }

}
