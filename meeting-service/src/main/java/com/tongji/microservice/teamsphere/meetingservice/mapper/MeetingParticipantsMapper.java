package com.tongji.microservice.teamsphere.meetingservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.meetingservice.entities.MeetingParticipants;
import com.tongji.microservice.teamsphere.meetingservice.entities.Meeting;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MeetingParticipantsMapper extends BaseMapper<MeetingParticipants> {
    @Select("SELECT DISTINCT meetingId FROM MeetingParticipants WHERE participantId = #{participantId}")
    List<String> selectMeetingIdsByParticipantId(int participantId);

    @Select("<script>" +
            "SELECT * FROM Meeting " +
            "WHERE id IN " +
            "<foreach item='meetingId' collection='meetingIds' open='(' separator=',' close=')'>" +
            "#{meetingId}" +
            "</foreach>" +
            "</script>")
    List<Meeting> selectMeetingsByMeetingIds(List<String> meetingIds);
}
