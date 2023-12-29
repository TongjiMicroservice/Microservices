package com.tongji.microservice.teamsphere.chatservice.socketio;

import cn.dev33.satoken.stp.StpUtil;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.tongji.microservice.teamsphere.chatservice.entities.MessageObject;
import com.tongji.microservice.teamsphere.chatservice.util.MongoDB;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.corundumstudio.socketio.SocketIOClient;
import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SocketIOService {
//    private static final Map<Integer, UUID> map= new HashMap<>();
    //双向Map
    //用于存储用户id和socket连接的映射关系
    private static final BiMap<Integer,UUID> biMap = HashBiMap.create();
    //用于连接用户数据库来获取用户信息
    @DubboReference(check = false)
    private UserService userService;
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
        System.out.println("Client disconnected: " + client.getSessionId());
        // 在和用户握手建立连接时，就进行token校验，获取客户端的 userId
//        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        String id_string =  client.getHandshakeData().getSingleUrlParam("id");
        if(StpUtil.isLogin())
            System.out.printf("登录成功");
        else
            System.out.printf("未登录");
        //token通过校验
        if(id_string != null){
            //将int转为String
            int userid = Integer.parseInt(id_string);
            //该用户已经在线，则先断开上一个用户的连接
            if(!biMap.containsKey(userid))
                server.getClient(biMap.get(userid)).disconnect();
            //将新的连接信息存入map
            biMap.put(userid, client.getSessionId());
        }
        //id校验失败，直接切断链接
        else{
            System.out.println("无效id");
            client.disconnect();
            return;
        }

        System.out.println("Client connected: " + client.getSessionId());
    });

        /*
          断开连接
          将用户的
         */
    server.addDisconnectListener(SocketIOService::connect);
    /*
      消息已读状态更替
      将所有sender发给receiver的消息状态改为已读
      q:id不应该由前端传递，应该由后端生成
     */
//    server.addEventListener("updateReadStatus", MessageObject.class, (client, data, ackRequest)-> {
//        int sender = data.getSenderId();
//        int receiver = data.getReceiverId();
//
//        //更新数据库
//        MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("chat");
//        Bson filter = Filters.and(
//                Filters.eq("sender", sender),
//                Filters.eq("receiver", receiver),
//                Filters.eq("isRead", false)
//        );
//// Create an update operation to set isRead to true
//        Bson updateOperation = Updates.set("isRead", true);
//// Update all matching documents in the collection
//        collection.updateMany(filter, updateOperation);
//
//        //通知对方消息已被阅读
//        server.getClient(biMap.get(sender)).sendEvent("readStatusUpdated", sender);
////        server.getBroadcastOperations().sendEvent("readStatusUpdated", sender);
//    });
//
//    /*
//      获取最近聊天对象
//     */
//    server.addEventListener("requestRecentChats", String.class, (client, data, ackRequest) -> {
//        System.out.println("Received userId message: " + data);
//        String userId=data;
//
//        MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("chat");
//        Set<String> contactIds = new HashSet<>();
//                // 查询 sender 或 receiver 为 userId 的记录
//                collection.find(
//                        Filters.or(
//                                Filters.eq("sender", userId),
//                                Filters.eq("receiver", userId)
//                        )
//                ).forEach(doc -> {
//                    String sender = doc.getString("sender");
//                    String receiver = doc.getString("receiver");
//                    if (!sender.equals(userId)) {
//                        contactIds.add(sender);
//                    }
//                    if (!receiver.equals(userId)) {
//                        contactIds.add(receiver);
//                    }
//                });
//
//                List<Document> contacts = new ArrayList<>();
//                for (String contactId : contactIds) {
//                    contacts.add(new Document("contactId", contactId));
//                    System.out.println(contactId);
//                }
//                client.sendEvent("recentChatResponse", contacts);
//    });
//
//    /*
//    查询聊天记录
//     */
//    server.addEventListener("fetchChatHistory", MessageObject.class, (client, data, ackRequest) -> {
//        int sender = data.getSenderId();
//        int receiver = data.getReceiverId();
//            // 从 MongoDB 中获取聊天记录
//               MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("chat");
////                List<Document> chatHistory = collection.find(
////                        Filters.or(Filters.eq("sender", sender), Filters.eq("sender", receiver))
////                ).sort(Sorts.ascending("timestamp")).into(new ArrayList<>());
//                Bson condition = Filters.or(
//                        Filters.and(Filters.eq("sender", sender), Filters.eq("receiver", receiver)),
//                        Filters.and(Filters.eq("sender", receiver), Filters.eq("receiver", sender))
//                );
//
//                List<Document> chatHistory = collection.find(condition)
//                        .sort(Sorts.ascending("timestamp"))
//                        .into(new ArrayList<>());
////                // 发送聊天记录回客户端
////                client.sendEvent("chatHistoryResponse", chatHistory);
//            // 发送聊天记录回客户端
//            client.sendEvent("chatHistoryResponse", chatHistory);
//
//    });
//
//    /*
//        处理接收到的消息
//     */
//    server.addEventListener("messageEvent", MessageObject.class, (client, data, ackRequest) -> {
//        System.out.println("Received message: " + data);
//        //聊天记录插入数据库
//        MongoCollection<Document> collection = MongoDB.getDatabase().getCollection("chat");
//        System.out.println("Connected successfully");
//        // 将 MessageObject 转换为 Document 并插入到集合中
//        Document doc = data.toDocument();
//        collection.insertOne(doc);
//        System.out.println("Document inserted successfully");
//
////            server.getBroadcastOperations().sendEvent("messageEvent", data);
//        //将消息发给对应的人
//        if(biMap.containsKey(data.getReceiverId())){
//            server.getClient(biMap.get(data.getReceiverId())).sendEvent("messageEvent", data);
//        }
//    });
//    server.addEventListener("simpleMessageEvent", String.class, (client, data, ackRequest) -> {
//        System.out.println("Received simple message: " + data);
//        // You can add further processing logic here
//    });
    server.start();
    }
    // 添加其他必要的方法...
    private static void connect(SocketIOClient client){
        if(biMap.containsValue(client.getSessionId())){
            biMap.remove(biMap.inverse().get(client.getSessionId()));
        }
        System.out.println("Client disconnected: " + client.getSessionId());
    }
}
