package com.tongji.microservice.teamsphere.fileservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("Star")
public class Star implements Serializable {
    @TableField("user_id")
    private int userId;
    @TableField("project_id")
    private int projectId;
}
