package com.github.tongji.microservice.consumerdemo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("service-provider-demo")
public interface ServiceProviderDemoClient {
    @RequestMapping("/echo/{string}")
    public ResponseEntity<EchoResponse> echo(@PathVariable String string);
}
