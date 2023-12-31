package com.tongji.microservice.teamsphere.dto.fileservice;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileResponse extends APIResponse implements Serializable {
    private List<FileData> list;
    public FileResponse(List<FileData> list){
        super(success());
        this.list = list;
    }
    public FileResponse(APIResponse response){
        super(response);
        this.list=null;
    }
}
