package com.tongji.microservice.teamsphere.meetingservice.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class TokenFetcher {
//    private Timer timer;

//    public void startTokenFetching() {
//        timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                // 在这里编写获取令牌的逻辑
//                // 可以调用相关的 API 或方法来获取最新的令牌
//                // 例如，调用获取令牌的方法
//                String token = fetchToken();
//                // 处理获取到的令牌，例如更新到全局变量或进行其他逻辑操作
//                processToken(token);
//            }
//        };
//        // 设置定时任务的延迟时间（以毫秒为单位）和间隔时间（以毫秒为单位）
//        long delay = 0; // 延迟时间，立即开始
//        long interval = 60 * 60 * 1000; // 间隔时间，这里设置为每小时获取一次令牌
//        // 启动定时任务
//        timer.scheduleAtFixedRate(task, delay, interval);
//    }

    public static String fetchToken() throws IOException {
        String url = "https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal";
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        // 设置请求的header
        post.setHeader("Content-Type", "application/json");

        // 设置请求的body
        JSONObject body = new JSONObject();
        body.put("app_id", "cli_a5f40dee38b9100c");
        body.put("app_secret", "bfxhA82NwRuF6KSSJO2YQec3B6KTdWqf");
        post.setEntity(new StringEntity(body.toString()));

        // 发送请求并获取响应
        HttpResponse response = client.execute(post);
        String jsonResponse = EntityUtils.toString(response.getEntity());

        // 解析JSON响应
        JSONObject responseObject = new JSONObject(jsonResponse);
        String token = responseObject.getString("tenant_access_token");

        return token;
//        // 将token写入文件
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("token.txt"))) {
//            writer.write(token);
//        }
    }
}
