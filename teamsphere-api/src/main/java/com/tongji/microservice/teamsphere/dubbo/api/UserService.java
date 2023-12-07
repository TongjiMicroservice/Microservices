package com.tongji.microservice.teamsphere.dubbo.api;

public interface UserService {
    boolean login(String username, String password);
    boolean register(String username, String password);
    String helloUserService();

    String callMemberService();
}
