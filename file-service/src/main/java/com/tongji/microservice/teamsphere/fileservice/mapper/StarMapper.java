package com.tongji.microservice.teamsphere.fileservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.fileservice.entities.Star;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StarMapper extends BaseMapper<Star> {

    @Select("SELECT file_id FROM Star WHERE user_id = #{userId}")
    List<Integer> getStarByUserId(@Param("userId")int userId);
}
