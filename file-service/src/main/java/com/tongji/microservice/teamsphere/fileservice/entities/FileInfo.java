package com.tongji.microservice.teamsphere.fileservice.entities;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("File")
public class FileInfo implements Serializable {
    @TableField("id")
    private int id;
    @TableField("url")
    private String url;
    @TableField("type")
    private String type;
    @TableField("name")
    private String name;
    @TableField("user_id")
    private int userId;
    @TableField("project_id")
    private int projectId;
    @TableField("upload_time")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime uploadTime;
//    private String uploadTime;
    @TableField("size")
    private int size;
}
