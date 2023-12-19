package com.tongji.microservice.teamsphere.gatewayservice.controller;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.AuthorizeResponse;
import com.tongji.microservice.teamsphere.dto.userservice.LoginResponse;
import com.tongji.microservice.teamsphere.dto.userservice.UserRequest;
import com.tongji.microservice.teamsphere.dto.userservice.RegisterResponse;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name= "UserController", description = "用户微服务接口")
public class UserController {
    @DubboReference(check = false)
    private UserService userService;

    @PostMapping("/user/login")
    @Operation(summary = "用户登录接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "用户不存在",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "密码错误",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = LoginResponse.class))),
    })
    public LoginResponse login(String username, String password) {
        return userService.login(username, password);
    }

    @PostMapping("/user/register")
    @Operation(summary = "用户注册接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = RegisterResponse.class))),
            @ApiResponse(responseCode = "400", description = "注册失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponse.class))),
    })
    public RegisterResponse register(UserRequest request) {
        return userService.register(request);
    }

    @GetMapping("/user/authorize")
    @Operation(summary = "用户鉴权接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = RegisterResponse.class))),
            @ApiResponse(responseCode = "400", description = "鉴权失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponse.class))),
    })
    public AuthorizeResponse authorize(String token) {
        return userService.authorize(token);
    }
}
