package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.*;

public interface UserService {

    LoginResponse login(String username, String password);
    RegisterResponse register(RegisterRequest request);

    AuthorizeResponse authorize(String token);

    APIResponse updateUserInfo(RegisterRequest request);
    UserResponse getUserInfo(int userId);

    QueryResponse queryUser(UserQueryRequest request);
    APIResponse deleteUser(int userId);
}

