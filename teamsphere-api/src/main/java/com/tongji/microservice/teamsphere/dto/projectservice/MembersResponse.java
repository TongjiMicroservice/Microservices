package com.tongji.microservice.teamsphere.dto.projectservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MembersResponse extends APIResponse implements Serializable {
   private List<MemberData> members;

    public MembersResponse(List<MemberData> members) {
         super(success());
         this.members = members;
    }

    public MembersResponse(APIResponse apiResponse) {
         super(apiResponse.getCode(), apiResponse.getMessage());
         this.members = null;
    }
}
