package com.tongji.microservice.teamsphere.chatservice.socketio;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;

/**
 * 用于配置SocketIO
 */
@org.springframework.context.annotation.Configuration
public class SocketIOConfig {
    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(9092); // 选择一个合适的端口
        System.out.printf(" SocketIO server started on port %d\n", config.getPort());
        return new SocketIOServer(config);
    }
}
