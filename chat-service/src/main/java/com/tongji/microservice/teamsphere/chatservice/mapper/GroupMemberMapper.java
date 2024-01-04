package com.tongji.microservice.teamsphere.chatservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.chatservice.entities.GroupMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GroupMemberMapper extends BaseMapper<GroupMember> {

    @Select("SELECT group_id FROM GroupMember WHERE user_id = #{userId}")
    List<Integer> getGroupByMember(@Param("userId") int userId);

    @Select("SELECT user_id FROM GroupMember WHERE group_id = #{groupId}")
    List<Integer> getMemberByGroupId(@Param("groupId") int groupId);
}
