package com.github.tongji.microservice.providerdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {
    @Value("${profile.info:dev}")
    private String profileInfo;

    @RequestMapping("/get/profile-info")
    public String getProfileInfo() {
        return profileInfo;
    }
}
