package com.tongji.microservice.teamsphere.entities.projectservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {
    private int id;
    private int projectId;
    private Long memberId;
    private String role;
}
