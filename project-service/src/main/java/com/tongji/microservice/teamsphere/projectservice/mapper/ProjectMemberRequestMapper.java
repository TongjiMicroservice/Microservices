package com.tongji.microservice.teamsphere.projectservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMemberRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProjectMemberRequestMapper extends BaseMapper<ProjectMemberRequest> {

    @Select("SELECT * FROM ProjectMemberRequest WHERE project_id = #{projectId}")
    List<ProjectMemberRequest> getRequestByProjectId(@Param("projectId") int projectId);

    @Update("UPDATE ProjectMemberRequest SET status = 1 WHERE user_id = #{userId} AND project_id = #{projectId}")
    int agree(@Param("userId")int userId, @Param("projectId")int projectId);
    @Update("UPDATE ProjectMemberRequest SET status = 2 WHERE user_id = #{userId} AND project_id = #{projectId}")
    int refuse(@Param("userId")int userId, @Param("projectId")int projectId);
}
