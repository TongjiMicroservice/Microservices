package com.tongji.microservice.teamsphere.chatservice.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.tongji.microservice.teamsphere.chatservice.entities.GroupChat;
import com.tongji.microservice.teamsphere.chatservice.entities.GroupMember;
import com.tongji.microservice.teamsphere.chatservice.mapper.GroupMapper;
import com.tongji.microservice.teamsphere.chatservice.mapper.GroupMemberMapper;
import com.tongji.microservice.teamsphere.chatservice.util.MongoDB;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.ContactObject;
import com.tongji.microservice.teamsphere.dto.chatservice.GroupMemberResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.RecentChatResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ChatService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tongji.microservice.teamsphere.dto.APIResponse.*;

@DubboService
public class ChatServiceImpl implements ChatService {

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupMemberMapper memberMapper;
    @DubboReference(check = false)
    private UserService userService;
    @Override
    public APIResponse getPort() {
        return success();
    }

    @Override
    public APIResponse createGroup(String groupName, String avatar, List<String> memberIds) {
        try {
            groupMapper.insert(new GroupChat(0, groupName, avatar));
            int groupId = groupMapper.getMaxId();
            for (var i : memberIds) {
                memberMapper.insert(new GroupMember(groupId, Integer.parseInt(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
            return fail(e.getMessage());
        }
        return success();
    }

    @Override
    public RecentChatResponse getRecentChat(int userId) {
        String str_id = String.valueOf(userId);
        MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("chat");
        Set<String> contactIds = new HashSet<>();
        // 查询 sender 或 receiver 为 userId 的记录
        collection.find(
                Filters.or(
                        Filters.eq("sender", str_id),
                        Filters.eq("receiver", str_id)
                )
        ).forEach(doc -> {
            String sender = doc.getString("sender");
            String receiver = doc.getString("receiver");
            if(receiver.charAt(0) != 'g' && sender.charAt(0) != 'g'){
                if (!sender.equals(str_id)) {
                    contactIds.add(sender);
                }
                if (!receiver.equals(str_id)) {
                    contactIds.add(receiver);
                }
            }
        });
        List<ContactObject> contacts = new ArrayList<>();
        for (String i : contactIds) {
            contacts.add(new ContactObject(i,userService.getUserInfo(Integer.parseInt(i)).getUsername()));
        }
        for(var i: memberMapper.getGroupByMember(userId)){
            contacts.add(new ContactObject('g'+i.toString(),groupMapper.selectById(i).getName()));
        }
        return new RecentChatResponse(contacts);
    }

    @Override
    public GroupMemberResponse getGroupMember(int groupId) {
        return null;
    }
}
