package com.tongji.microservice.teamsphere.dto.fileservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileDataResponse extends APIResponse implements Serializable {
    private FileData fileData;
    public FileDataResponse(APIResponse apiResponse ,FileData fileData){
        super(apiResponse.getCode(),apiResponse.getMessage());
        this.fileData = fileData;
    }
}
