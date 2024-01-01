package com.tongji.microservice.teamsphere.chatservice.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("GroupMember")
public class GroupMember {
    @TableField("group_id")
    private int groupId;
    @TableField("user_id")
    private int userId;
}
