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
    private String avatar;

    public ContactObject(Integer id, String name) {
        this.id = id.toString();
        this.name = name;
        this.avatar = "";
    }
}
