package com.tongji.microservice.teamsphere.dto.projectservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberData implements Serializable {
    private int userId;
    private int projectId;
    private int privilege;
    private String name;
    private String avatar;
}
