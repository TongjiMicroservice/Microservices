package com.tongji.microservice.teamsphere.impl;

import com.tongji.microservice.teamsphere.dubbo.api.MemberService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
public class MemberServiceImpl implements MemberService {
    @Override
    public String helloMemberService() {
        return "Hello MemberService!";
    }
}
