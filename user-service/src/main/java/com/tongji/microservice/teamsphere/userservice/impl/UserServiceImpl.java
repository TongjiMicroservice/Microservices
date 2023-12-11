package com.tongji.microservice.teamsphere.userservice.impl;

import com.tongji.microservice.teamsphere.dubbo.api.MemberService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserServiceImpl implements UserService {
    @DubboReference(check = false)
    private MemberService memberService;

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean register(String username, String password) {
        return false;
    }

    @Override
    public String helloUserService() {
        return "Hello UserService!";
    }

    @Override
    public String callMemberService() {
        return memberService.helloMemberService();
    }
}
