package com.tongji.microservice.teamsphere.projectservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMember;
import org.apache.ibatis.annotations.Select;

public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {
    @Select("SELECT privilege FROM ProjectMember WHERE id = #{projectId} AND user_id = #{userId}")
    int getPrivilege(int projectId, int userId);
    
}
