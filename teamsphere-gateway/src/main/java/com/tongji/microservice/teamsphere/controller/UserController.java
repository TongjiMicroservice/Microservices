package com.tongji.microservice.teamsphere.controller;

import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @DubboReference(check = false)
    private UserService userService;

    @GetMapping("/user/hello")
    public String HelloUserService() {
        return userService.helloUserService();
    }

    @GetMapping("/user/callMemberService")
    public String callMemberService() {
        return userService.callMemberService();
    }
}
