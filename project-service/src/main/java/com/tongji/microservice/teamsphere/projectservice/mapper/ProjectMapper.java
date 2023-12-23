package com.tongji.microservice.teamsphere.projectservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.projectservice.entities.Project;
import org.apache.ibatis.annotations.Select;

public interface ProjectMapper extends BaseMapper<Project> {
    // 查询项目列表
    @Select("select * from project")
    public Project[] selectAll();
}
