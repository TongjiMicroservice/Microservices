package com.tongji.microservice.teamsphere.gatewayservice.controller;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.*;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
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
    public RegisterResponse register(RegisterRequest request) {
        return userService.register(request);
    }

    @GetMapping("/user/authorize")
    @Operation(summary = "用户鉴权接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = AuthorizeResponse.class))),
            @ApiResponse(responseCode = "400", description = "鉴权失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizeResponse.class))),
    })
    public AuthorizeResponse authorize(String token) {
        return userService.authorize(token);
    }

    @GetMapping("user/info")
    @Operation(summary = "获取用户详细信息接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    public UserResponse getUserDetails(int userId) {
        return userService.getUserInfo(userId);
    }

    @GetMapping("user")
    @Operation(summary = "查询用户接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = QueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QueryResponse.class))),
    })
    public QueryResponse queryUser(String token, UserQueryRequest request) {
        return userService.queryUser(token, request);
    }

    @PostMapping("user")
    @Operation(summary = "更新用户信息接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = QueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QueryResponse.class))),
    })
    APIResponse updateUserInfo(String token, RegisterRequest request){
        return userService.updateUserInfo(token, request);
    }

    @DeleteMapping("user")
    @Operation(summary = "删除用户接口", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = QueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QueryResponse.class))),
    })
    APIResponse deleteUser(String token, int userId){
        return userService.deleteUser(token, userId);
    }
}
