package com.tongji.microservice.teamsphere.dto.userservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
public class QueryResponse extends APIResponse implements Serializable {
    private UserData[] data;
    public QueryResponse(APIResponse apiResponse, UserData[] data) {
        // 调用父类的构造函数来设置状态码和消息
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.data = data;
    }
}
