package com.tongji.microservice.teamsphere.gatewayservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfig implements WebMvcConfigurer {
    @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")  //指定允许跨域请求的路径模式为"/**"，表示所有的路径都将允许跨域访问。
                    .allowedOrigins("*") // 允许访问资源的域名
                    .allowedMethods("*") // 允许的HTTP方法
                    .allowedHeaders("*") // 允许的请求头
                    .allowCredentials(false) // 是否允许发送凭证信息
                    .maxAge(3600); // 预检请求的有效期
        }
}
