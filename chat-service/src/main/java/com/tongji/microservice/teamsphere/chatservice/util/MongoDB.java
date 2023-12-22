package com.tongji.microservice.teamsphere.chatservice.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    static{
        try{
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("db");
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
