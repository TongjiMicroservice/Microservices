package com.tongji.microservice.teamsphere.dto.fileservice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FileData implements Serializable {
    private String url;
    private String type;
    private String name;
    private LocalDateTime uploadTime;
    private int userId;
    private int projectId;
    private int size;
}
