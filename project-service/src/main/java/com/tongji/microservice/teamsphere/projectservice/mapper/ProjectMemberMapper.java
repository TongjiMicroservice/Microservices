package com.tongji.microservice.teamsphere.projectservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.dto.projectservice.MemberData;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {
    @Select("SELECT ProjectMember.privilege FROM ProjectMember WHERE project_id = #{projectId} AND user_id = #{userId}")
    int getPrivilege(@Param("userId") int userId, @Param("projectId")int projectId);

    @Select("SELECT * FROM ProjectMember WHERE project_id = #{projectId}")
    List<ProjectMember> getMembers(@Param("projectId")int projectId);

    @Select("SELECT ProjectMember.project_id FROM ProjectMember WHERE user_id = #{userId}")
    List<Integer>getProjectByUserId(@Param("userId") int userId);
}
