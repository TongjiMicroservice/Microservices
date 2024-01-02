package com.tongji.microservice.teamsphere.dto.fileservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileData implements Serializable {
    private int id;
    private String url;
    private String type;
    private String name;
    private LocalDateTime uploadTime;
//    private String uploadTime;
    private int userId;
    private int projectId;
    private int size;
    private int starred;
}
