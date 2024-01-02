package com.tongji.microservice.teamsphere.dto.chatservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RecentChatResponse extends APIResponse implements Serializable {
    private List<ContactObject> list;

    public RecentChatResponse(List<ContactObject> list) {
        super(success());
        this.list = list;
    }
    public RecentChatResponse(APIResponse response){
        super(response);
        this.list = null;
    }


}
