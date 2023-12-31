package com.tongji.microservice.teamsphere.fileservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
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
    private LocalDateTime uploadTime;
    @TableField("size")
    private int size;
}
