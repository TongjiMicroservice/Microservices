package com.tongji.microservice.teamsphere.projectservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {
    @Select("SELECT ProjectMember.privilege FROM ProjectMember WHERE project_id = #{projectId} AND user_id = #{userId}")
    int getPrivilege(@Param("userId") int userId, @Param("projectId")int projectId);
    
}
