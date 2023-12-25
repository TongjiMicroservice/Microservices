package com.tongji.microservice.teamsphere.dto.sceduleservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventIdResponse extends APIResponse implements Serializable {
    private int scheduleId;

    public EventIdResponse(APIResponse apiResponse, int scheduleId) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.scheduleId = scheduleId;
    }

    // 错误的生成导致没有scheduleId,scheduleId 被设置为-1
    public EventIdResponse(APIResponse apiResponse){
        super(apiResponse.getCode(),apiResponse.getMessage());
        scheduleId = -1;
    }
}
