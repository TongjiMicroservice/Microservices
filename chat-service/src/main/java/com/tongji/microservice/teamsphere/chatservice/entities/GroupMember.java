package com.tongji.microservice.teamsphere.chatservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("GroupMember")
public class GroupMember {
    @TableField("group_id")
    private int groupId;
    @TableField("user_id")
    private String userId;
}
