package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.LoginResponse;
import com.tongji.microservice.teamsphere.dto.userservice.UserRequest;
import com.tongji.microservice.teamsphere.dto.userservice.RegisterResponse;
import com.tongji.microservice.teamsphere.dto.userservice.UserResponse;

public interface UserService {

    LoginResponse login(String username, String password);
    RegisterResponse register(UserRequest request);

    APIResponse updateUserDetails(UserRequest request);
    UserResponse getUserDetails(int userId);
    APIResponse deleteUser(int userId);
}

