package com.tongji.microservice.teamsphere.projectservice.entities;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tongji.microservice.teamsphere.dto.projectservice.ProjectData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("Project")
public class Project {
    @TableField("id")
    private int id;
    @TableField("name")
    private String name;
    @TableField("description")
    private String description;
    @TableField("scale")
    private int scale;
    @TableField("leader")
    private int leader;

    public Project(String name, String description, int scale, int leader) {
        this.name = name;
        this.description = description;
        this.scale = scale;
        this.leader = leader;
    }

    public Project(ProjectData projectData) {
        this.name = projectData.getName();
        this.description = projectData.getDescription();
        this.scale = projectData.getScale();
        this.leader = projectData.getLeader();
    }

}
