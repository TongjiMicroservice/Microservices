package com.tongji.microservice.teamsphere.chatservice.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.tongji.microservice.teamsphere.chatservice.entities.MessageObject;
import com.tongji.microservice.teamsphere.chatservice.mapper.GroupMemberMapper;
import com.tongji.microservice.teamsphere.chatservice.util.MongoDB;
import com.tongji.microservice.teamsphere.dto.chatservice.ContactObject;
import com.tongji.microservice.teamsphere.dto.chatservice.RecentChatResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ChatService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SocketIOService {
    //    private static final Map<Integer, UUID> map= new HashMap<>();
    //双向Map
    //用于存储用户id和socket连接的映射关系
    private static final BiMap<Integer, UUID> biMap = HashBiMap.create();
    //用于连接用户数据库来获取用户信息
    @DubboReference(check = false)
    private UserService userService;
    @DubboReference(check = false)
    private ChatService chatService;
    private final SocketIOServer server;

    @Autowired
    public SocketIOService(SocketIOServer server) {
        this.server = server;
    }

    @PostConstruct
    public void startServer() {
        System.out.println("正在启动服务器... Starting SocketIO server...");
        /*
          用户首次连接ws服务器
          1. 获取用户信息（和哪些人聊过天）
          2. 获取用户未读消息
          3. 获取系统消息
         */
        server.addConnectListener(client -> {
            System.out.println("连接:" + client.getSessionId() + '@' + client.getRemoteAddress());
        });
        server.addEventListener("loginRequest", String.class, (client, id_string, ackRequest) -> {
            //token通过校验
            if (id_string != null) {
                //将int转为String
                int userid = Integer.parseInt(id_string);
                //该用户已经在线，则先将该用户id绑定到最新连接的SessionId
                if (biMap.containsKey(userid))
                    if(biMap.get(userid) != client.getSessionId())
                        biMap.replace(userid, client.getSessionId());
//                    server.getClient(biMap.get(userid)).disconnect();
                //将新的连接信息存入map
                biMap.put(userid, client.getSessionId());
                System.out.printf("连接成功,session%s绑定到%d\n", client.getSessionId(), userid);
                //广播通知所有在线成员
                server.getBroadcastOperations().sendEvent("loginResponse",id_string);
            }
            //id校验失败，直接切断链接
            else {
                System.out.println("无id参数");
                client.disconnect();
            }
        });
        /*
          断开连接
          将用户从bimap中移除
          通知其他用户该用户下线
         */
        server.addDisconnectListener(client ->{
            System.out.println("注销" + client.getSessionId());
            if (biMap.containsValue(client.getSessionId())) {
                int  userId = biMap.inverse().get(client.getSessionId());
                biMap.remove(userId);
                server.getBroadcastOperations().sendEvent("logoutResponse",String.valueOf(userId));
            }
        });
        /*
          消息已读状态更替
          将所有sender发给receiver的消息状态改为已读
          q:id不应该由前端传递，应该由后端生成
         */
        server.addEventListener("acknowledgeRequest", String.class, (client, senderId, ackRequest) -> {
            System.out.println("收到消息已读状态更替请求:"+senderId);
            if(senderId.charAt(0) == 'g')
                return;
            String receiverId = String.valueOf(biMap.inverse().get(client.getSessionId()));
            //更新数据库
            MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("chat");
            Bson filter = Filters.and(
                    Filters.eq("sender", senderId),
                    Filters.eq("receiver", receiverId),
                    Filters.eq("isRead", false)
            );
// Create an update operation to set isRead to true
            Bson updateOperation = Updates.set("isRead", true);
// Update all matching documents in the collection
            collection.updateMany(filter, updateOperation);

            //通知对方消息已被阅读
            server.getClient(biMap.get(Integer.parseInt(senderId))).sendEvent("acknowledgeResponse", senderId);
//        server.getBroadcastOperations().sendEvent("readStatusUpdated", sender);
        });

        /*
          查询聊天记录
         */
        server.addEventListener("chatHistoryRequest", String.class, (client, data, ackRequest) -> {
            System.out.printf("sessionId:%s\n", client.getSessionId());
            String selfId = biMap.inverse().get(client.getSessionId()).toString();
            String src = data;
            //群聊
            MongoCollection<Document> collection;
            Bson condition;
            if(src.charAt(0)=='g'){
                collection = MongoDB.getDatabase().getCollection("chat");
//                List<Document> chatHistory = collection.find(
//                        Filters.or(Filters.eq("sender", sender), Filters.eq("sender", receiver))
//                ).sort(Sorts.ascending("timestamp")).into(new ArrayList<>());
                condition = Filters.eq("receiver", src);
            }
            //私聊
            else{
                collection = MongoDB.getDatabase().getCollection("chat");
//                List<Document> chatHistory = collection.find(
//                        Filters.or(Filters.eq("sender", sender), Filters.eq("sender", receiver))
//                ).sort(Sorts.ascending("timestamp")).into(new ArrayList<>());
                condition = Filters.or(
                        Filters.and(Filters.eq("sender", selfId), Filters.eq("receiver", src)),
                        Filters.and(Filters.eq("sender", src), Filters.eq("receiver", selfId))
                );

            }
            // 从 MongoDB 中获取聊天记录
            List<Document> chatHistory = collection.find(condition)
                    .sort(Sorts.ascending("timestamp"))
                    .into(new ArrayList<>());
//                // 发送聊天记录回客户端
//                client.sendEvent("chatHistoryResponse", chatHistory);
            // 发送聊天记录回客户端
            client.sendEvent("chatHistoryResponse", chatHistory);
            System.out.println("Chat history sent to client: " + chatHistory);
        });

        /*
          处理接收到的消息
         */
        server.addEventListener("messageRequest", MessageObject.class, (client, data, ackRequest) -> {
            System.out.println("收到消息：" + data);
            //聊天记录插入数据库
            MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("chat");
            // 将 MessageObject 转换为 Document 并插入到集合中
            Document doc = data.toDocument();
            server.getBroadcastOperations().sendEvent("messageEvent", data);
            //群消息标为已读
            if(doc.getString("receiver").charAt(0) == 'g'){
                doc.replace("isRead", true);
            }
            //私聊，如果对应的人在线，将消息发给对应的人
            else {
                int id = Integer.parseInt(data.getReceiverId());
                if (biMap.containsKey(id)) {
                    server.getClient(biMap.get(id)).sendEvent("messageEvent", data);
                }
            }
            collection.insertOne(doc);
            System.out.println("Document inserted successfully");
        });

        server.addEventListener("recentChatRequest", String.class,(client, userIdStr, ackRequest) -> {
            //将最近聊天消息发给用户
            try{
                int userId = Integer.parseInt(userIdStr);
                var list = chatService.getRecentChat(userId).getList();
                client.sendEvent("recentChatResponse", list);
                System.out.println("发送最近聊天消息成功"+list);
                //发送聊天用户的名字
                Map<Integer,String> nameMap = new HashMap<>();
                for(var groupId: chatService.getGroups(userId)){
                    for(var i : chatService.getGroupMember(groupId).getList()){
                        if(!nameMap.containsKey(i))
                            nameMap.put(i , userService.getUserInfo(i).getUsername());
                    }
                }
                List<ContactObject> groupMemberNameList = new ArrayList<>();
                nameMap.forEach((id, name) -> {
                    groupMemberNameList.add(new ContactObject(id,name));
                });
                client.sendEvent("groupNameResponse" , groupMemberNameList);
                System.out.println("最近群聊成员成功"+groupMemberNameList);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("发送最近聊天消息失败");
            }
        });

        server.start();
    }
}
