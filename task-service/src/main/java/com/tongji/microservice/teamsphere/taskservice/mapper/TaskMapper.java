package com.tongji.microservice.teamsphere.taskservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.taskservice.entities.Task;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TaskMapper extends BaseMapper<Task> {
    @Select("SELECT project_id FROM Task WHERE id = #{taskId}")
    int getProjectId(@Param("taskId")int taskId);

    @Delete("DELETE FROM Task WHERE id = #{taskId}")
    int deleteTaskById(@Param("taskId")int taskId);

    @Select("SELECT * FROM Task WHERE project_id = #{projectId}")
    List<Task> selectTaskByProjectId(@Param("projectId") int projectId);

    @Select("SELECT id FROM Task WHERE project_id = #{projectId} AND name = #{name}")
    int getTaskId(@Param("projectId") int projectId, @Param("name") String name);

    @Select("SELECT * FROM Task WHERE id = #{id}")
    Task getTaskById(@Param("id") int id);

    @Select("SELECT * FROM Task WHERE leader = #{userId}")
    Task[] getTaskByLeader(@Param("userId") int userId);
}
