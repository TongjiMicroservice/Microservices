package com.tongji.microservice.teamsphere.projectservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMemberRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProjectMemberRequestMapper extends BaseMapper<ProjectMemberRequest> {

    @Select("SELECT * FROM project_member_request WHERE project_id = #{projectId}")
    List<ProjectMemberRequest> getRequestByProjectId(@Param("projectId") int projectId);
}
