package com.tongji.microservice.teamsphere.userservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("User")
@NoArgsConstructor
public class User {
    @TableField("id")
    public int id;
    @TableField("username")
    public String username;
    @TableField("password")
    public String password;
    @TableField("email")
    public String email;
    @TableField("avatar")
    public String avatar;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.email = "";
        this.avatar= "";
    }
    public User(String username, String password, String email, String avatar) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar= avatar;
    }
}
