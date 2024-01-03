package com.tongji.microservice.teamsphere.meetingservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.*;
import com.tongji.microservice.teamsphere.dubbo.api.MeetingService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.meetingservice.client.FeishuAPIClient;
import com.tongji.microservice.teamsphere.meetingservice.client.MeetingBackData;
import com.tongji.microservice.teamsphere.meetingservice.entities.Meeting;
import com.tongji.microservice.teamsphere.meetingservice.entities.MeetingParticipants;
import com.tongji.microservice.teamsphere.meetingservice.mapper.MeetingMapper;
import com.tongji.microservice.teamsphere.meetingservice.mapper.MeetingParticipantsMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@DubboService
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private MeetingParticipantsMapper participantsMapper;

    @DubboReference(check = false)
    private UserService userService;


    @Override
    public APIResponse cancelMeeting(String meetingId) {
        FeishuAPIClient client = new FeishuAPIClient();
        QueryWrapper<Meeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", meetingId);
        Meeting meeting = meetingMapper.selectOne(queryWrapper);
//        Meeting meeting = meetingMapper.selectMeetingByBookId(meetingId);
        if (meeting == null) {
            return new APIResponse(400, "会议不存在");
        } else {
            boolean isDatabaseSuccess = meetingMapper.deleteMeetingById(meeting.getBookId()) > 0; // 成功删除数据库中的数据
            participantsMapper.deleteMeetingById(meeting.getId());
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
    public MeetingResponse createMeeting(int projectId, String title, String description, LocalDateTime starTime,
                                         LocalDateTime deadline) {
        LocalDateTime currentTime = LocalDateTime.now();

        // 判断 starttime 和 deadline 是否在当前时间之后
        if (starTime.isBefore(currentTime)) {
            return new MeetingResponse(new APIResponse(406, "会议开始时间必须在未来时间"), null);
        }

        // 判断 deadline 是否在 starttime 之后
        if (deadline.isBefore(starTime)) {
            return new MeetingResponse(new APIResponse(407, "会议结束时间必须在开始时间后"), null);
        }

        FeishuAPIClient client = new FeishuAPIClient();
        MeetingBackData meetingBackData;
        try{
            meetingBackData = client.BookMeeting(deadline);
//            System.out.println("预约会议测试返回数据打印");
//            System.out.println(meetingBackData.status);
//            System.out.println(meetingBackData.url);
            if (!meetingBackData.status) {
                return new MeetingResponse(new APIResponse(400, "预约会议失败"), null);
            } else {
                Meeting meeting = new Meeting(meetingBackData.id, projectId, title, description, starTime, deadline,
                        meetingBackData.url, meetingBackData.bookId);
                boolean isDatabaseSuccess = meetingMapper.insert(meeting) > 0;
                System.out.println("会议录入数据库结果：");
                System.out.println(isDatabaseSuccess);

                MeetingData backmeetingdata =new MeetingData(
                        meeting.id,
                        meeting.projectId,
                        meeting.title,
                        meeting.description,
                        meeting.startTime,
                        meeting.duration,
                        meeting.url,
                        meeting.bookId
                );
//                System.out.println(backmeetingdata);
                if (isDatabaseSuccess)
                    return new MeetingResponse(new APIResponse(200, "预约会议成功"), backmeetingdata);
                else
                    return new MeetingResponse(new APIResponse(201, "预约会议成功,但录入数据库失败"), backmeetingdata);
            }
        }catch (Exception e){
            System.out.println(e);
            return new MeetingResponse(new APIResponse(404, "网络解析失败"), null);
        }
    };

    @Override
    public MeetingListResponse getMeetingsForProject(int projectId) {
        List<Meeting> meetings = meetingMapper.selectMeetingsByProjectId(projectId);
        List<MeetingData> meetingDataList = meetings.stream()
                .filter(Objects::nonNull)
                .map(meeting -> new MeetingData(
                        meeting.id,
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
            return new MeetingListResponse(new APIResponse(400, "没有相关会议"), null);
        } else {
            List<Meeting> meetings = new ArrayList<>(); // 创建一个ArrayList对象来存储Meeting对象
            for(String item : meetingIds){
                Meeting meeting = meetingMapper.selectMeetingById(item);
                meetings.add(meeting);
            }
            List<MeetingData> meetingDataList = meetings.stream()
                    .filter(Objects::nonNull)
                    .map(meeting -> new MeetingData(
                            meeting.id,
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
                return new MeetingListResponse(new APIResponse(401, "获取个人会议信息失败"), null);
            } else {
                return new MeetingListResponse(new APIResponse(200, "获取个人会议信息成功"), meetingDataList);
            }
        }
    };

    @Override
    public APIResponse addParticipant(String meetingId, int participantId, String role) {
        QueryWrapper<MeetingParticipants> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("meeting_id", meetingId);
        queryWrapper.eq("participant_id", participantId);
        MeetingParticipants existingParticipant = participantsMapper.selectOne(queryWrapper);

        QueryWrapper<Meeting> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", meetingId);
        Meeting existingMeeting = meetingMapper.selectOne(queryWrapper1);
        if(existingMeeting == null){
            return new APIResponse(401, "会议不存在");
        }
        if (existingParticipant != null) {
            return new APIResponse(400, "参会人已存在");
        }

        MeetingParticipants participant = new MeetingParticipants(meetingId, participantId, role);
        boolean isDatabaseSuccess = participantsMapper.insert(participant) > 0;

        if (isDatabaseSuccess) {
            return new APIResponse(200, "添加参会人成功");
        } else {
            return new APIResponse(400, "添加参会人失败");
        }
    }

    @Override
    public APIResponse removeParticipant(String meetingId, int participantId){
        QueryWrapper<MeetingParticipants> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("meeting_id", meetingId);
        queryWrapper.eq("participant_id", participantId);
        MeetingParticipants participant = participantsMapper.selectOne(queryWrapper);

        QueryWrapper<Meeting> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", meetingId);
        Meeting existingMeeting = meetingMapper.selectOne(queryWrapper1);
        if(existingMeeting == null){
            return new APIResponse(401, "会议不存在");
        }
        if (participant == null) {
            return new APIResponse(400, "参会人不存在");
        } else {
            boolean isDatabaseSuccess = participantsMapper.delete(queryWrapper) > 0; // 成功删除数据库中的数据
                if (isDatabaseSuccess)
                    return new APIResponse(200, "参会人移除成功");
                else
                    return new APIResponse(401, "参会人移除失败");
        }
    }

    @Override
    public APIResponse setParticipantRole(String meetingId, int participantId, String role){
        QueryWrapper<MeetingParticipants> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("meeting_id", meetingId);
        queryWrapper.eq("participant_id", participantId);
        MeetingParticipants participant = participantsMapper.selectOne(queryWrapper);

        QueryWrapper<Meeting> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", meetingId);
        Meeting existingMeeting = meetingMapper.selectOne(queryWrapper1);
        if(existingMeeting == null){
            return new APIResponse(401, "会议不存在");
        }
        if (participant == null) {
            return new APIResponse(400, "参会人不存在");
        } else {
            participant.setRole(role);
            boolean isDatabaseSuccess = participantsMapper.update(participant,queryWrapper) > 0; // 成功删除数据库中的数据
            if (isDatabaseSuccess)
                return new APIResponse(200, "参会人角色修改成功");
            else
                return new APIResponse(401, "参会人角色修改失败");
        }
    }

    @Override
    public ParticipantListResponse getParticipantsForMeeting(String meetingId) {
        QueryWrapper<Meeting> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", meetingId);
        Meeting existingMeeting = meetingMapper.selectOne(queryWrapper1);
        if(existingMeeting == null){
            return new ParticipantListResponse(new APIResponse(401, "会议不存在"),null);
        }

        QueryWrapper<MeetingParticipants> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("meeting_id", meetingId);
        List<MeetingParticipants> participants = participantsMapper.selectList(queryWrapper);

        List<ParticipantData> participantDataList = participants.stream()
                .filter(Objects::nonNull)
                .map(participant -> new ParticipantData(
                        participant.id,
                        userService.getUserInfo(participant.participantId).getUsername(),
                        participant.meetingId,
                        participant.participantId,
                        participant.role
                ))
                .collect(Collectors.toList());


        return new ParticipantListResponse(new APIResponse(200, "成功获取参会人列表"), participantDataList);
    }
}
