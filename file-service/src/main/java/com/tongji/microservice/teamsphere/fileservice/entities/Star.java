package com.tongji.microservice.teamsphere.fileservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Star")
public class Star implements Serializable {
    @TableField("user_id")
    private int userId;
    @TableField("file_id")
    private int fileId;
}
