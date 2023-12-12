package com.tongji.microservice.teamsphere.gatewayservice.controller;

import com.tongji.microservice.teamsphere.dto.userservice.LoginResponse;
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

    @GetMapping("/user/hello")
    @Operation(summary = "用户微服务测试接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public String HelloUserService() {
        return userService.helloUserService();
    }

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
    public RegisterResponse register(String username, String password) {
        return userService.register(username, password);
    }

    @GetMapping("/user/callMemberService")
    @Operation(summary = "用户微服务调用会员微服务测试接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public String callMemberService() {
        return userService.callMemberService();
    }
}
