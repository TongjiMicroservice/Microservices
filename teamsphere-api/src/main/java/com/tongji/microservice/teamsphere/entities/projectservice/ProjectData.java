package com.tongji.microservice.teamsphere.entities.projectservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectData {
    private int id;
    private int scale;
    private String name;
    private String description;
    private int leader;


}
