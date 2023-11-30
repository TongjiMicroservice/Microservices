package com.github.tongji.microservice.providerdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @Value("${spring.application.name:demo}")
    private String serviceName;
    @RequestMapping(value = "/echo/{string}",method = RequestMethod.GET)
    public ResponseEntity<EchoResponse> echo(@PathVariable String string) {
        return new ResponseEntity<>(new EchoResponse(serviceName,string), HttpStatus.OK);
    }
}
