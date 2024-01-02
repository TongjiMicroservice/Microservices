package com.tongji.microservice.teamsphere.chatservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.chatservice.entities.GroupChat;
import org.apache.ibatis.annotations.Select;

public interface GroupMapper extends BaseMapper<GroupChat> {

    @Select("SELECT MAX(id) FROM GroupChat")
    int getMaxId();
}
