package com.tongji.microservice.teamsphere.chatservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.chatservice.entities.Group;
import org.apache.ibatis.annotations.Select;

public interface GroupMapper extends BaseMapper<Group> {

    @Select("SELECT MAX(id) FROM Group")
    int getMaxId();
}
