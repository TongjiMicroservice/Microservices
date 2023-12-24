package com.tongji.microservice.teamsphere.projectservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("ProjectMember")
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMember {
    @TableField("project_id")
    private int projectId;
    @TableField("user_id")
    private int userId;
    @TableField("privilege")
    private int privilege;//1普通成员 2管理员 3超级管理员

}
