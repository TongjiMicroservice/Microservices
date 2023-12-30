package com.tongji.microservice.teamsphere.fileservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileData;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;
import com.tongji.microservice.teamsphere.dubbo.api.FileService;
import com.tongji.microservice.teamsphere.fileservice.entities.FileInfo;
import com.tongji.microservice.teamsphere.fileservice.mapper.FileMapper;
import com.tongji.microservice.teamsphere.fileservice.mapper.StarMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DubboService
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private StarMapper starMapper;
    @Override
    public APIResponse upload(int userId, int projectId, byte[] file) {
        return null;
    }

    @Override
    public FileResponse getFileByProject(int projectId) {
        System.out.printf("id:%d\n",projectId);
        List<FileData> list = new ArrayList<>();
        for(var i :  fileMapper.getFileByProject(projectId)){
            list.add(new FileData(
                    i.getUrl(),
                    i.getType(),
                    i.getName(),
                    i.getUploadTime(),
                    i.getUserId(),
                    i.getProjectId(),
                    i.getSize()
            ));
        }
        System.out.printf("list size:%d\n",list.size());
        return new FileResponse(list);
    }

    @Override
    public FileResponse getFileByStar(int userId) {
        var stars =  starMapper.getStarByUserId(userId);
        List<FileData> list = new ArrayList<>();
        for(var star : stars){
            FileInfo i = fileMapper.getFileById(star);
            list.add(new FileData(
                    i.getUrl(),
                    i.getType(),
                    i.getName(),
                    i.getUploadTime(),
                    i.getUserId(),
                    i.getProjectId(),
                    i.getSize()
            ));
        }
        return new FileResponse(list);
    }
}
