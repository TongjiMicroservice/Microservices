package com.tongji.microservice.teamsphere.meetingservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.meetingservice.entities.MeetingParticipants;
import com.tongji.microservice.teamsphere.meetingservice.entities.Meeting;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MeetingParticipantsMapper extends BaseMapper<MeetingParticipants> {
    @Select("SELECT DISTINCT meeting_id FROM MeetingParticipants WHERE participant_id = #{participantId}")
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
