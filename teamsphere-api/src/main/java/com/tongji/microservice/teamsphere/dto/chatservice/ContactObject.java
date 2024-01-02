package com.tongji.microservice.teamsphere.dto.chatservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactObject implements Serializable {
    private String id;
    private String name;
}