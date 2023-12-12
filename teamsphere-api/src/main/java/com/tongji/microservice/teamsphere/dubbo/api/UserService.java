package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.LoginResponse;
import com.tongji.microservice.teamsphere.dto.userservice.RegisterResponse;
import com.tongji.microservice.teamsphere.dto.userservice.UserData;

public interface UserService {
    LoginResponse login(String username, String password);
    RegisterResponse register(String username, String password);
    String helloUserService();

    String callMemberService();
}

