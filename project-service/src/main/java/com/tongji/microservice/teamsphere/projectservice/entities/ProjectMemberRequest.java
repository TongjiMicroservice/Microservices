package com.tongji.microservice.teamsphere.projectservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ProjectMemberRequest")
public class ProjectMemberRequest implements Serializable {
    @TableField("user_id")
    private int userId;
    @TableField("project_id")
    private int projectId;
    @TableField("request_time")
    private LocalDateTime requestTime;
    @TableField("status")
    private int status;
}
