package com.tongji.microservice.teamsphere.entities.userservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData implements Serializable {
    private int id;
    private String username;
}
