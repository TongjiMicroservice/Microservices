package com.tongji.microservice.teamsphere.chatservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Group")
public class Group {
    @TableField("id")
    private int id;
    @TableField("name")
    private String name;
    @TableField("description")
    private String description;

}
