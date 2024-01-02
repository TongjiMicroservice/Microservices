package com.tongji.microservice.teamsphere.taskservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.dto.taskservice.TaskMemberData;
import com.tongji.microservice.teamsphere.taskservice.entities.TaskMember;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskMemberMapper extends BaseMapper<TaskMember> {
    @Insert("INSERT INTO TaskMember (task_id, user_id) VALUES (#{taskId}, #{userId})")
    int addMember(@Param("taskId")int taskId, @Param("userId")int userId);

    @Delete("DELETE FROM TaskMember WHERE task_id = #{taskId} AND user_id = #{userId}")
    int deleteMember(@Param("taskId")int taskId, @Param("userId")int userId);

    @Update("UPDATE TaskMember SET score = #{score} WHERE task_id = #{taskId} AND user_id = #{userId}")
    int setScore(@Param("taskId")int taskId, @Param("userId")int userId,  @Param("score")int score);
    @Update("UPDATE TaskMember SET (file_url, finish_time) = (#{fileURL},#{time}) WHERE task_id = #{taskId} AND user_id = #{userId}")
    int setFileURL(@Param("taskId")int taskId, @Param("userId")int userId, @Param("fileURL")String fileURL, @Param("time")LocalDateTime time);

    @Select("SELECT * FROM TaskMember WHERE task_id = #{taskId}")
    List<TaskMember> getMembersByTaskId(@Param("taskId") int taskId);

    @Select("SELECT task_id FROM TaskMember WHERE user_id = #{userId}")
    List<Integer> getTaskByUserId(@Param("userId") int userId);
}
