package com.github.tongji.microservice.consumerdemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class EchoService {
    private final ServiceProviderDemoClient serviceProviderDemoClient;

    @Autowired
    public EchoService(ServiceProviderDemoClient serviceProviderDemoClient) {
        this.serviceProviderDemoClient = serviceProviderDemoClient;
    }

    @RequestMapping(value = "/echo/{string}",method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        ResponseEntity<EchoResponse> result = serviceProviderDemoClient.echo(string);
        var respBody= result.getBody();
        if (respBody != null) {
            return respBody.serviceName()+":"+respBody.echoString()+"\ngit提交集成部署测试4";
        }else {
            return "error";
        }
    }
}
