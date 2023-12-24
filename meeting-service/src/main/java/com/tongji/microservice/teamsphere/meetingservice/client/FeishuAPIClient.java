package com.tongji.microservice.teamsphere.meetingservice.client;

import com.tongji.microservice.teamsphere.meetingservice.entities.*;

import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.vc.v1.model.*;
import java.util.HashMap;
import com.lark.oapi.core.request.RequestOptions;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import com.tongji.microservice.teamsphere.meetingservice.client.MeetingBackData;


public class FeishuAPIClient {
    private static Client client;
    // 这是使用tenant_access_token访问必传的参数，一个用户一个应用专属的id， 由于user_acccess
    // token需要不断刷新，这里不采用
    private static final String tenant_access_token = "t-g104coc9LQHMV66OODEVB7R43NNONFDXM7LKYKHA";
    private static final String open_id = "ou_3b3fb663eca94b776c1d5f921ccf602b";

    public FeishuAPIClient() throws Exception {
        // 构建client
        client = Client.newBuilder("cli_a5f40dee38b9100c", "bfxhA82NwRuF6KSSJO2YQec3B6KTdWqf")
                .marketplaceApp() // 设置 app 类型为商店应用
                .openBaseUrl(BaseUrlEnum.FeiShu) // 设置域名，默认为飞书
                // .requestTimeout(5,TimeUnit.SECONDS) // 设置httpclient 超时时间，默认永不超时
                .disableTokenCache() // 禁用token管理，禁用后需要开发者自己传递token
                .logReqAtDebug(true) // 在 debug 模式下会打印 http 请求和响应的 headers,body 等信息。
                .build();
    }

    // region app_access_token
    // 获取应用的app_access_token,这是原定使用user_access_token所需的一个参数，这里弃用
    public static void GetAppAccessToken() throws Exception {
        // 创建请求对象
        AppAccessTokenInternalAuthReq req = AppAccessTokenInternalAuthReq.newBuilder()
                .appAccessTokenInternalAuthReqBody(AppAccessTokenInternalAuthReqBody.newBuilder()
                        .appId("cli_a5f40dee38b9100c")
                        .appSecret("bfxhA82NwRuF6KSSJO2YQec3B6KTdWqf")
                        .build())
                .build();

        // 发起请求
        // 如开启了Sdk的token管理功能，就无需调用 RequestO
        // tions.newBuilder().tenantAccessToken("t-xxx").build()来设置租户token了
        AppAccessTokenInternalAuthResp resp = client.auth().auth().appAccessTokenInternal(req,
                RequestOptions.newBuilder()
                        .tenantAccessToken("")
                        .build());

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(
                    String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(),
                            resp.getRequestId()));
            return;
        }

        // 业务数据处理
        System.out.println(Jsons.DEFAULT.toJson(resp.getData()));
    }
    // endregion

    // 将LocalDateTime转为Unix时间戳（以秒为单位）
    public static String localDateTimeToUnixTimestamp(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return String.valueOf(instant.getEpochSecond());
    }

    // 预约会议
    public MeetingBackData BookMeeting(LocalDateTime deadline) throws Exception {
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

            MeetingBackData meetingBackData = new MeetingBackData();
            // 处理 reserve 对象
            if (reserve != null) {
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
