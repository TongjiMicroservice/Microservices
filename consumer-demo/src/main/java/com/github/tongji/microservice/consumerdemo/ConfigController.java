package com.github.tongji.microservice.consumerdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {
    @Value("${name:demo}")
    private String name;

    @RequestMapping("/get/name")
    public String getName() {
        return name;
    }
}
