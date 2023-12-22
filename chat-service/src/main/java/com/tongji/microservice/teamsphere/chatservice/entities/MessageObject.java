package com.tongji.microservice.teamsphere.chatservice.entities;

import lombok.Data;
import org.bson.Document;

@Data
public class MessageObject {
    private String senderId;
    private String receiverId;
    private String message;
    private String timestamp; // 新增时间戳字段
    private boolean isRead; // 新增 isRead 字段

    @Override
    public String toString() {
        return "MessageObject{" +
                "sender='" + senderId + '\'' +
                ", receiver='" + receiverId + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", isRead=" + isRead +
                '}';
    }

    public Document toDocument() {
        return new Document("sender", senderId)
                .append("receiver", receiverId)
                .append("message", message)
                .append("timestamp", timestamp)
                .append("isRead", isRead); // 将 isRead 也添加到 Document
    }
}
