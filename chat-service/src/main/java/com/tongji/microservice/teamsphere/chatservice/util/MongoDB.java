package com.tongji.microservice.teamsphere.chatservice.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    static{
        try{
            System.out.println("正在连接MongoDB...");
            mongoClient = MongoClients.create("mongodb://123.60.177.179:27017");
            database = mongoClient.getDatabase("test");
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
}
