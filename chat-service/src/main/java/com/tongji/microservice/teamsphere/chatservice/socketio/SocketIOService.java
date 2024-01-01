package com.tongji.microservice.teamsphere.chatservice.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.tongji.microservice.teamsphere.chatservice.entities.MessageObject;
import com.tongji.microservice.teamsphere.chatservice.util.MongoDB;
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
            System.out.println("Client connected: " + client.getSessionId());
            // 在和用户握手建立连接时，就进行token校验，获取客户端的 userId
//          String userId = client.getHandshakeData().getSingleUrlParam("userId");
            String id_string = client.getHandshakeData().getSingleUrlParam("id");
            //token通过校验
            if (id_string != null) {
                //将int转为String
                int userid = Integer.parseInt(id_string);
                //该用户已经在线，则先断开上一个用户的连接
                if (!biMap.containsKey(userid))
                    server.getClient(biMap.get(userid)).disconnect();
                //将新的连接信息存入map
                biMap.put(userid, client.getSessionId());
                System.out.println("连接成功");
                //广播通知所有在线成员
                server.getBroadcastOperations().sendEvent("login",id_string);
                //将最近聊天消息发给用户
                client.sendEvent("recentChatResponse",chatService.getRecentChat(userid).getList());
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
            System.out.println("Client disconnected: " + client.getSessionId());
            if (biMap.containsValue(client.getSessionId())) {
                int  userId = biMap.inverse().get(client.getSessionId());
                biMap.remove(userId);
                server.getBroadcastOperations().sendEvent("logout",userId);
            }
        });
        /*
          消息已读状态更替
          将所有sender发给receiver的消息状态改为已读
          q:id不应该由前端传递，应该由后端生成
         */
        server.addEventListener("acknowledgeRequest", MessageObject.class, (client, data, ackRequest) -> {
            int sender = data.getSenderId();
            int receiver = data.getReceiverId();

            //更新数据库
            MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("chat");
            Bson filter = Filters.and(
                    Filters.eq("sender", sender),
                    Filters.eq("receiver", receiver),
                    Filters.eq("isRead", false)
            );
// Create an update operation to set isRead to true
            Bson updateOperation = Updates.set("isRead", true);
// Update all matching documents in the collection
            collection.updateMany(filter, updateOperation);

            //通知对方消息已被阅读
            server.getClient(biMap.get(sender)).sendEvent("acknowledgeResponse", sender);
//        server.getBroadcastOperations().sendEvent("readStatusUpdated", sender);
        });

        /*
          查询聊天记录
         */
        server.addEventListener("chatHistoryRequest", String.class, (client, data, ackRequest) -> {
            String selfId = biMap.inverse().get(client.getSessionId()).toString();
            String src = data;
            //群聊
            MongoCollection<Document> collection;
            Bson condition;
            if(src.charAt(1)=='g'){
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
        });

        /*
          处理接收到的消息
         */
        server.addEventListener("message", MessageObject.class, (client, data, ackRequest) -> {
            System.out.println("Received message: " + data);
            //聊天记录插入数据库
            MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("chat");
            // 将 MessageObject 转换为 Document 并插入到集合中
            Document doc = data.toDocument();
            collection.insertOne(doc);
            System.out.println("Document inserted successfully");

//            server.getBroadcastOperations().sendEvent("messageEvent", data);
            //如果对应的人在线，将消息发给对应的人
            if (biMap.containsKey(data.getReceiverId())) {
                server.getClient(biMap.get(data.getReceiverId())).sendEvent("messageEvent", data);
            }
        });

        server.start();
    }
}
