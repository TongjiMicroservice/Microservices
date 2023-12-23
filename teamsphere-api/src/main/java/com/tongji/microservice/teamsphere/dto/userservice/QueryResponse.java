package com.tongji.microservice.teamsphere.dto.userservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.entities.userservice.UserData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
public class QueryResponse extends APIResponse implements Serializable {
    private List<UserData> data;
    public QueryResponse(APIResponse apiResponse, List<UserData> data) {
        // 调用父类的构造函数来设置状态码和消息
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.data = data;
    }

    public QueryResponse(APIResponse apiResponse) {
        super(apiResponse.getCode(), apiResponse.getMessage());
        this.data = null;
    }
}
