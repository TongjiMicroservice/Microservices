package com.tongji.microservice.teamsphere.meetingservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.meetingservice.entities.Meeting;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface MeetingMapper extends BaseMapper<Meeting> {
    @Select("SELECT * FROM meeting WHERE id = #{id}")
    Meeting selectMeetingById(String id);

    @Insert("INSERT INTO meeting (id, projectId, title, description, starTime, endTime, meetingUrl, bookId) VALUES (#{id}, #{projectId},#{title}, #{description},#{starTime}, #{endTime},#{meetingUrl}, #{bookId}")
    int insertMeeting(Meeting meeting);

    @Delete("DELETE FROM meeting WHERE bookId = #{bookId}")
    int deleteMeetingById(String bookId);

    @Select("SELECT * FROM meeting WHERE projectId = #{projectId}")
    List<Meeting> selectMeetingsByProjectId(String projectId);
}
