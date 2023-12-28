package com.tongji.microservice.teamsphere.dto.projectservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectData implements Serializable {
    private int id;
    private int scale;
    private String name;
    private String description;
    private int leader;
}
