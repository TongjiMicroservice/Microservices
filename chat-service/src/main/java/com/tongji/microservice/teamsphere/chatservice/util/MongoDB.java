package com.tongji.microservice.teamsphere.chatservice.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDB {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    static{
        try{
            System.out.println("正在连接MongoDB...");
            mongoClient = MongoClients.create(
                    "mongodb://remote0:a6b62ad5f7cb0690598af747c83b29a233b55c719d221429fb3a73995b1f8d39@123.60.177.179:27017/chat");
            database = mongoClient.getDatabase("chat");
            System.out.println("Connected successfully");
        } catch (Exception e) {
            System.err.println("Error while inserting to MongoDB: " + e.getMessage());
        }
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static MongoDatabase getDatabase() {
    return database;
    }
//    public static void main(String[] args){
//        try{
//            MongoCollection<Document> collection = database.getCollection("chat");
//            // 将 MessageObject 转换为 Document 并插入到集合中
//            Document doc = new Document("sender", "10")
//                    .append("receiver", "g100")
//                    .append("message", "hello,world")
//                    .append("timestamp", "2013-3-23T12:00:00.102Z")
//                    .append("isRead", false);
//            collection.insertOne(doc);
//            System.out.println("Document inserted successfully");
//        } catch (Exception e) {
//            System.err.println("Error while inserting to MongoDB: " + e.getMessage());
//        }
//    }
}
