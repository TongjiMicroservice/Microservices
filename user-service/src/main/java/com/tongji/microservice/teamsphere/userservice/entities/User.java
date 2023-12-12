package com.tongji.microservice.teamsphere.userservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("user")
@NoArgsConstructor
public class User {
    @TableField("id")
    public int id;
    @TableField("name")
    public String username;
    @TableField("password")
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
