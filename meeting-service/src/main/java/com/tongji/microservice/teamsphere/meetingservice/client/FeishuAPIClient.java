package com.tongji.microservice.teamsphere.meetingservice.client;

import com.lark.oapi.Client;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.auth.v3.model.InternalTenantAccessTokenReq;
import com.lark.oapi.service.auth.v3.model.InternalTenantAccessTokenReqBody;
import com.lark.oapi.service.auth.v3.model.InternalTenantAccessTokenResp;
import com.lark.oapi.service.vc.v1.model.*;
import com.tongji.microservice.teamsphere.meetingservice.utils.TokenFetcher;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class FeishuAPIClient {
    private static Client client;
    // 这是使用tenant_access_token访问必传的参数，一个用户一个应用专属的id， 由于user_acccess
    // token刷新需要登录态，这里不采用
    private static String tenant_access_token = "t-g104cskBBQKFNOSIXGPWWCOTWYQ3E6CPTS3N3MC2";
    private static final String open_id = "ou_3b3fb663eca94b776c1d5f921ccf602b";
    private static final String app_id = "cli_a5f40dee38b9100c";
    private static final String app_secret = "bfxhA82NwRuF6KSSJO2YQec3B6KTdWqf";

    public FeishuAPIClient(){
        // 构建client
        client = Client.newBuilder("cli_a5f40dee38b9100c", "bfxhA82NwRuF6KSSJO2YQec3B6KTdWqf")
                .marketplaceApp() // 设置 app 类型为商店应用
                // .requestTimeout(5,TimeUnit.SECONDS) // 设置httpclient 超时时间，默认永不超时
                .disableTokenCache() // 禁用token管理，禁用后需要开发者自己传递token
                .logReqAtDebug(true) // 在 debug 模式下会打印 http 请求和响应的 headers,body 等信息。
                .build();
    }

    // 获取自建应用的tenant_access_token
    public void GetTenantAccessToken(){
        // 创建请求对象
        InternalTenantAccessTokenReq req = InternalTenantAccessTokenReq.newBuilder()
                .internalTenantAccessTokenReqBody(InternalTenantAccessTokenReqBody.newBuilder()
                        .appId(app_id)
                        .appSecret(app_secret)
                        .build())
                .build();
        // 发起请求
        try {
            InternalTenantAccessTokenResp resp = client.auth().v3().tenantAccessToken().internal(req, RequestOptions.newBuilder()
                    .tenantAccessToken("")
                    .build());

            // 处理服务端错误
            if (!resp.success()) {
                System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
                return;
            } else {
                // 业务数据处理
                //！！！！！！！接口有问题
//                TenantAccessTokenResp reserve = resp.getData();
//                tenant_access_token = reserve.getTenantAccessToken();
            }
        }catch (Exception e){

        }
    }

    // 将LocalDateTime转为Unix时间戳（以秒为单位）
    public static String localDateTimeToUnixTimestamp(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return String.valueOf(instant.getEpochSecond());
    }

    // 预约会议
    public MeetingBackData BookMeeting(LocalDateTime deadline) throws Exception {
        tenant_access_token = TokenFetcher.fetchToken(); // 防止2小时的token过期
        System.out.println("更新后的tenant_access_token是");
        System.out.println(tenant_access_token);
        // 创建请求对象
        ApplyReserveReq req = ApplyReserveReq.newBuilder()
                .applyReserveReqBody(ApplyReserveReqBody.newBuilder()
                        .endTime(localDateTimeToUnixTimestamp(deadline))
                        .ownerId(open_id)
                        .meetingSettings(ReserveMeetingSetting.newBuilder()
                                .build())
                        .build())
                .build();

        // 发起请求
        ApplyReserveResp resp = client.vc().reserve().apply(req, RequestOptions.newBuilder()
                .tenantAccessToken(tenant_access_token)
                .build());

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(
                    String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(),
                            resp.getRequestId()));
            MeetingBackData meetingBackData = new MeetingBackData();
            meetingBackData.status = false;
            return meetingBackData;
        } else {
            // 业务数据处理
            Reserve reserve = resp.getData().getReserve();

            System.out.println("BookMeeting Response:");
            System.out.println(reserve.getMeetingNo());
            System.out.println(reserve.getUrl());
            System.out.println(reserve.getId());

            MeetingBackData meetingBackData = new MeetingBackData();
            // 处理 reserve 对象
            if (reserve != null) {
                System.out.println("返回的MeetingBackData正常");
                meetingBackData.status = true;
                meetingBackData.bookId = reserve.getId();
                meetingBackData.id = reserve.getMeetingNo();
                meetingBackData.url = reserve.getUrl();
            } else {
                meetingBackData.status = false;
            }
            return meetingBackData;
        }
    }

    // 取消会议(需要的是预约会议的这个预约id，不是会议id)
    public boolean CancelMeeting(String bookId) throws Exception {
        tenant_access_token = TokenFetcher.fetchToken(); // 防止2小时的token过期
        // 创建请求对象
        DeleteReserveReq req = DeleteReserveReq.newBuilder()
                .reserveId(bookId)
                .build();

        // 发起请求
        DeleteReserveResp resp = client.vc().reserve().delete(req, RequestOptions.newBuilder()
                .tenantAccessToken(tenant_access_token)
                .build());

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(
                    String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(),
                            resp.getRequestId()));
            return false;
        }

        // 业务数据处理
        System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
        return true;
    }
}
