package com.tongji.microservice.teamsphere.fileservice.impl;

import com.obs.services.exception.ObsException;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectRequest;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileData;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;
import com.tongji.microservice.teamsphere.dubbo.api.FileService;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.fileservice.entities.FileInfo;
import com.tongji.microservice.teamsphere.fileservice.entities.Star;
import com.tongji.microservice.teamsphere.fileservice.mapper.FileMapper;
import com.tongji.microservice.teamsphere.fileservice.mapper.StarMapper;
import com.tongji.microservice.teamsphere.fileservice.util.Loader;
import org.apache.dubbo.config.annotation.DubboReference;
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
    @DubboReference(check = false)
    private ProjectService projectService;
    @Override
    public APIResponse upload(FileData fileData) {
        try {
            var fi = new FileInfo();
            fi.setId(0);
            fi.setUrl(fileData.getUrl());
            fi.setType(fileData.getType());
            fi.setName(fileData.getName());
            fi.setUserId(fileData.getUserId());
            fi.setProjectId(fileData.getProjectId());
            fi.setUploadTime(fileData.getUploadTime());
            fileMapper.insert(fi);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return success();
    }

    @Override
    public APIResponse delete(int userId, String fileName) {
        try {
            FileInfo f = fileMapper.getFileByName(fileName);
            if(f.getUserId() != userId){
                return fail("文件不属于你");
            }
            starMapper.deleteByFileId(f.getId());
            fileMapper.deleteById(f.getId());
            return success();
        }catch (Exception e){
            e.printStackTrace();
            return fail("删除失败:"+e.getMessage());
        }

    }

    @Override
    public FileResponse getFileByProject(int projectId) {
        System.out.printf("id:%d\n",projectId);
        List<FileData> list = new ArrayList<>();
        var l = fileMapper.getFileByProject(projectId);
        for(var i : l){
            System.out.println("i.getUploadTime()");
            System.out.println(i.getUploadTime());
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
        //return new FileResponse(fail("error"));
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
