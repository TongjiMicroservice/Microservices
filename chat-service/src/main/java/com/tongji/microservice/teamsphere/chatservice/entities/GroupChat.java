package com.tongji.microservice.teamsphere.chatservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("GroupChat")
public class GroupChat {
    @TableField("id")
    private int id;
    @TableField("name")
    private String name;
    @TableField("avatar")
    private String avatar;

}
