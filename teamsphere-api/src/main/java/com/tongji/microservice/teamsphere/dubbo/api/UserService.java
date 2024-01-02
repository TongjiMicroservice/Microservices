package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.*;

public interface UserService {

    LoginResponse checkID(String username, String password);
    RegisterResponse register(RegisterRequest request);

    AuthorizeResponse authorize(String token);

    APIResponse updateUserInfo(int userId,RegisterRequest request);
    UserResponse getUserInfo(int userId);

    APIResponse deleteUser(int userId);

    UserResponse getUserInfoByEmail(String email);
}

