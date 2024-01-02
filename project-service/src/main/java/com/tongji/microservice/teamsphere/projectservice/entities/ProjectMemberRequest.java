package com.tongji.microservice.teamsphere.projectservice.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("ProjectMemberRequest")
public class ProjectMemberRequest implements Serializable {
    private int userId;
    private int projectId;
    private LocalDateTime requestTime;
    private int status;
}
