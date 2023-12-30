package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;

public interface FileService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    APIResponse upload(int userId, int projectId, byte[] file);
    FileResponse getFileByProject(int projectId);
    FileResponse getFileByStar(int userId);
}
