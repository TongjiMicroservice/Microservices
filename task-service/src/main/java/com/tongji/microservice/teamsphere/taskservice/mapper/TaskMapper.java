package com.tongji.microservice.teamsphere.taskservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.taskservice.entities.Task;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TaskMapper extends BaseMapper<Task> {
    @Select("SELECT project_id FROM task WHERE task_id = #{taskId}")
    int getProjectId(@Param("taskId")int taskId);

    @Delete("DELETE FROM task WHERE task_id = #{taskId}")
    int deleteTaskById(@Param("taskId")int taskId);

    @Select("SELECT * FROM task WHERE project_id = #{projectId}")
    List<Task> selectTaskByProjectId(@Param("projectId") int projectId);
}
