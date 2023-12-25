package com.tongji.microservice.teamsphere.scheduleservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.scheduleservice.entities.Event;
import org.apache.ibatis.annotations.Select;

public interface EventMapper extends BaseMapper<Event> {
    @Select("select max(id) from event")
    int getMaxId()  ;
}
