package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.userservice.LoginResponse;
import com.tongji.microservice.teamsphere.dto.userservice.RegisterResponse;
import com.tongji.microservice.teamsphere.entities.userservice.User;

public interface UserService {
    LoginResponse login(String username, String password);
    RegisterResponse register(String username, String password);

    //test
    String helloUserService();

    //test
    String callMemberService();

    void updateUserDetails(String userId, String newUsername, String newEmail);
    User getUserDetails(String userId);
    void deleteUser(String userId);
}

