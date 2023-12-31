package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileData;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;

public interface FileService {

    APIResponse upload(FileData fileData);

    APIResponse delete(int userId, String fileName);
    FileResponse getFileByProject(int projectId);
    APIResponse putStar(int userId, int fileId);
    APIResponse deleteStar(int userId, int fileId);
    FileResponse getFileByStar(int userId);
}
