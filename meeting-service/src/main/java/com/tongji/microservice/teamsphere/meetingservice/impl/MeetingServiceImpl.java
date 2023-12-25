package com.tongji.microservice.teamsphere.meetingservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingData;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingListResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingResponse;
import com.tongji.microservice.teamsphere.dubbo.api.MeetingService;
import com.tongji.microservice.teamsphere.meetingservice.client.FeishuAPIClient;
import com.tongji.microservice.teamsphere.meetingservice.client.MeetingBackData;
import com.tongji.microservice.teamsphere.meetingservice.entities.Meeting;
import com.tongji.microservice.teamsphere.meetingservice.mapper.MeetingMapper;
import com.tongji.microservice.teamsphere.meetingservice.mapper.MeetingParticipantsMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@DubboService
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private MeetingParticipantsMapper participantsMapper;

    @Override
    public APIResponse cancelMeeting(String meetingId) {
        FeishuAPIClient client = new FeishuAPIClient();
        QueryWrapper<Meeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", meetingId);
        Meeting meeting = meetingMapper.selectOne(queryWrapper);
        if (meeting == null) {
            return new APIResponse(400, "会议不存在");
        } else {
            boolean isDatabaseSuccess = meetingMapper.deleteMeetingById(meeting.getBookId()) > 0; // 成功删除数据库中的数据
            try {
                boolean isFeishunSuccess = client.CancelMeeting(meeting.getBookId());
                if (isDatabaseSuccess && isFeishunSuccess)
                    return new APIResponse(200, "会议取消成功");
                else {
                    if (!isDatabaseSuccess && isFeishunSuccess)
                        return new APIResponse(401, "会议未能从数据库中删除");
                    else if (isDatabaseSuccess && !isFeishunSuccess)
                        return new APIResponse(402, "会议未能从飞书中删除");
                    else
                        return new APIResponse(403, "会议未能从数据库和飞书中删除");
                }
            }catch (Exception e){
                System.out.println(e);
                return new APIResponse(404, "会议取消失败");
            }
        }
    };

    @Override
    public MeetingResponse createMeeting(String projectId, String title, String description, LocalDateTime starTime,
                                         LocalDateTime deadline) {
        FeishuAPIClient client = new FeishuAPIClient();
        MeetingBackData meetingBackData;
        try{
            meetingBackData = client.BookMeeting(deadline);
            if (meetingBackData.status == false) {
                return new MeetingResponse(new APIResponse(400, "预约会议失败"), null);
            } else {
                Meeting meeting = new Meeting(meetingBackData.id, projectId, title, description, starTime, deadline,
                        meetingBackData.url, meetingBackData.bookId);
                boolean isDatabaseSuccess = meetingMapper.insertMeeting(meeting) > 0;
                if (isDatabaseSuccess)
                    return new MeetingResponse(new APIResponse(200, "预约会议成功"), meetingBackData.url);
                else
                    return new MeetingResponse(new APIResponse(201, "预约会议成功,但录入数据库失败"), meetingBackData.url);
            }
        }catch (Exception e){
            System.out.println(e);
            return new MeetingResponse(new APIResponse(400, "预约会议失败"), null);
        }
    };

    @Override
    public MeetingListResponse getMeetingsForProject(String projectId) {
        List<Meeting> meetings = meetingMapper.selectMeetingsByProjectId(projectId);
        List<MeetingData> meetingDataList = meetings.stream()
                .map(meeting -> new MeetingData(
                        Integer.parseInt(meeting.id),
                        meeting.projectId,
                        meeting.title,
                        meeting.description,
                        meeting.startTime,
                        meeting.duration,
                        meeting.url,
                        meeting.bookId
                ))
                .collect(Collectors.toList());
        if (meetings == null) {
            return new MeetingListResponse(new APIResponse(400, "获取项目会议信息失败"), null);
        } else if (meetings.isEmpty()) {
            return new MeetingListResponse(new APIResponse(201, "项目会议数量为0"), meetingDataList);
        } else {
            return new MeetingListResponse(new APIResponse(200, "获取项目会议信息成功"), meetingDataList);
        }
    };

    @Override
    public MeetingListResponse getMeetingsForUser(int userId) {
        List<String> meetingIds = participantsMapper.selectMeetingIdsByParticipantId(userId);
        if (meetingIds == null) {
            return new MeetingListResponse(new APIResponse(400, "获取个人会议信息失败"), null);
        } else {
            List<Meeting> meetings = participantsMapper.selectMeetingsByMeetingIds(meetingIds);
            List<MeetingData> meetingDataList = meetings.stream()
                    .map(meeting -> new MeetingData(
                            Integer.parseInt(meeting.id),
                            meeting.projectId,
                            meeting.title,
                            meeting.description,
                            meeting.startTime,
                            meeting.duration,
                            meeting.url,
                            meeting.bookId
                    ))
                    .collect(Collectors.toList());
            if (meetings == null) {
                return new MeetingListResponse(new APIResponse(400, "获取个人会议信息失败"), null);
            } else {
                return new MeetingListResponse(new APIResponse(200, "获取个人会议信息成功"), meetingDataList);
            }
        }
    };
}
