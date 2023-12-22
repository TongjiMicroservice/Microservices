package com.tongji.microservice.teamsphere.projectservice.entities;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@TableName("Project")
public class Project {
    private int id;
    private String name;
    private String description;
    private LocalDateTime createTime;
}
