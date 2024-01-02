package com.tongji.microservice.teamsphere.dto.userservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryRequest implements Serializable {
    private int userid;
    private String username,email;
}
