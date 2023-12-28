package com.tongji.microservice.teamsphere.projectservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.dto.projectservice.ProjectData;
import com.tongji.microservice.teamsphere.projectservice.entities.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {
    // 查询项目列表
    @Select("select * from Project")
    public List<Project> selectAll();

    @Select("select * from Project where id = #{id}")
    ProjectData selectProjectById(@Param("id") int id);
}
