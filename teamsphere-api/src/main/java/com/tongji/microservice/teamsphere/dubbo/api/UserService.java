package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.*;

public interface UserService {

    LoginResponse login(String username, String password);
    RegisterResponse register(UserRequest request);

    AuthorizeResponse authorize(String token);

    APIResponse updateUserDetails(UserRequest request);
    UserResponse getUserDetails(int userId);
    APIResponse deleteUser(int userId);
}

