package com.tongji.microservice.teamsphere.dto.chatservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GroupMemberResponse extends APIResponse implements Serializable {
    private List<Integer> list;
    public GroupMemberResponse(List<Integer> list) {
        super(success());
        this.list = list;
    }
    public GroupMemberResponse(APIResponse response){
        super(response);
        this.list = null;
    }
}
