package com.tongji.microservice.teamsphere.scheduleservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.scheduleservice.entities.Event;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EventMapper extends BaseMapper<Event> {
    @Select("select max(id) from event")
    int getMaxId()  ;

    @Select("SELECT COUNT(*) FROM event WHERE id = #{userId} AND starttime BETWEEN (SELECT MIN(starttime) FROM event) AND (SELECT MAX(deadline) FROM event)")
    int getCountOfEvents(@Param("userId") int userId);

    @Select("SELECT userid FROM event WHERE id = #{eventId}")
    int getUserIdByEventId(@Param("eventId") int eventId);


}
