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

    @Select("SELECT * FROM meeting WHERE book_id = #{book_id}")
    Meeting selectMeetingByBookId(String book_id);

    @Insert("INSERT INTO meeting (id, project_id, title, description, star_time, duration, meeting_url, book_id) VALUES (#{id}, #{projectId},#{title}, #{description},#{starTime}, #{duration},#{meetingUrl}, #{bookId}")
    int insertMeeting(Meeting meeting);

    @Delete("DELETE FROM meeting WHERE book_id = #{bookId}")
    int deleteMeetingById(String bookId);

    @Select("SELECT * FROM meeting WHERE project_id = #{projectId}")
    List<Meeting> selectMeetingsByProjectId(int projectId);

    @Select("SELECT EXISTS (SELECT 1 FROM meeting WHERE book_id = #{bookId})")
    boolean existsMeetingByBookId(String bookId);
}
