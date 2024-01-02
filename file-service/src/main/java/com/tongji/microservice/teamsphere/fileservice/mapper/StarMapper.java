package com.tongji.microservice.teamsphere.fileservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.fileservice.entities.Star;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StarMapper extends BaseMapper<Star> {

    @Select("SELECT file_id FROM Star WHERE user_id = #{userId}")
    List<Integer> getStarByUserId(@Param("userId")int userId);

    @Delete("DELETE FROM Star WHERE user_id = #{userId} AND file_id = #{fileId}")
    void deleteStar(@Param("userId") int userId, @Param("fileId") int fileId);

    @Delete("DELETE FROM Star WHERE file_id = #{fileId}")
    void deleteByFileId(@Param("fileId") int fileId);

    @Select("SELECT COUNT(*) FROM Star WHERE user_id = #{userId} AND file_id = #{fileId}")
    int isStarred(@Param("userId") int userId, @Param("fileId") int fileId);
}
