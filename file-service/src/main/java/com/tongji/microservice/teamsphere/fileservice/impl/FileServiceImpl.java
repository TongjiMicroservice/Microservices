package com.tongji.microservice.teamsphere.fileservice.impl;

import com.obs.services.exception.ObsException;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectRequest;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileData;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;
import com.tongji.microservice.teamsphere.dubbo.api.FileService;
import com.tongji.microservice.teamsphere.fileservice.entities.FileInfo;
import com.tongji.microservice.teamsphere.fileservice.entities.Star;
import com.tongji.microservice.teamsphere.fileservice.mapper.FileMapper;
import com.tongji.microservice.teamsphere.fileservice.mapper.StarMapper;
import com.tongji.microservice.teamsphere.fileservice.util.Loader;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.tongji.microservice.teamsphere.dto.APIResponse.fail;
import static com.tongji.microservice.teamsphere.dto.APIResponse.success;

@DubboService
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private StarMapper starMapper;
    @Override
    public APIResponse upload(FileData fileData) {
        try {
            fileMapper.insert(new FileInfo(
                    0,
                    fileData.getUrl(),
                    fileData.getType(),
                    fileData.getName(),
                    fileData.getUserId(),
                    fileData.getProjectId(),
                    fileData.getUploadTime(),
                    fileData.getSize()
            ));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return success();
    }

    @Override
    public FileResponse getFileByProject(int projectId) {
        System.out.printf("id:%d\n",projectId);
        List<FileData> list = new ArrayList<>();
        for(var i : fileMapper.getFileByProject(projectId)){
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
    public APIResponse putStar(int userId, int fileId) {
        try {
            starMapper.insert(new Star(userId, fileId));
        }catch (Exception e){
            return fail(e.getMessage());
        }
        return success();
    }

    @Override
    public APIResponse deleteStar(int userId, int fileId) {
        try{
            starMapper.deleteStar(userId, fileId);
        }catch (Exception e){
            return fail(e.getMessage());
        }
        return success();
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
