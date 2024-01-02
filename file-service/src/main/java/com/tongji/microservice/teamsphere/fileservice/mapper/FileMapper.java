package com.tongji.microservice.teamsphere.fileservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.fileservice.entities.FileInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FileMapper extends BaseMapper<FileInfo> {

    @Select("SELECT * FROM File WHERE user_id = #{userId}")
    List<FileInfo> getFileByUserId(@Param("userId") int userId);

    @Select("SELECT * FROM File WHERE project_id = #{projectId}")
    List<FileInfo> getFileByProject(@Param("projectId") int projectId);

    @Select("SELECT * FROM File WHERE id = #{fileId}")
    FileInfo getFileById(@Param("fileId") int fileId);

    @Select("SELECT * FROM File WHERE name = #{fileName}")
    FileInfo getFileByName(@Param("fileName") String fileName);

    @Select("SELECT * FROM File WHERE url = #{url}")
    FileInfo getFileByURL(@Param("url") String url);
}
