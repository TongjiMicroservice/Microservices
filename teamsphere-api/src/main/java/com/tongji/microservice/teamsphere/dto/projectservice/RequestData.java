package com.tongji.microservice.teamsphere.dto.projectservice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestData implements Serializable {
    private int userId;
    private String name;
    private LocalDateTime requestTime;
    private int status;
}
