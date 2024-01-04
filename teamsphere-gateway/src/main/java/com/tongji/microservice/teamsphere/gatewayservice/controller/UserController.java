package com.tongji.microservice.teamsphere.gatewayservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
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
@RequestMapping("/api")
@Tag(name= "UserController", description = "用户微服务接口")
public class UserController {
    @DubboReference(check = false)
    private UserService userService;

    @PostMapping("/user/login")
    @Operation(summary = "用户登录", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "用户不存在",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "密码错误",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = LoginResponse.class))),
    })
    public SaResult login(String username, String password) {
        var res=userService.checkID(username, password);
        if (res.getCode()==200){
            StpUtil.login(res.getUserid());
            var info=StpUtil.getTokenInfo();
            return SaResult.data(info);
        }else {
            return SaResult.error(res.getMessage());
        }
    }

    @PostMapping("/user/register")
    @Operation(summary = "用户注册", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = RegisterResponse.class))),
            @ApiResponse(responseCode = "400", description = "注册失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponse.class))),
    })
    public RegisterResponse register(RegisterRequest request) {
        return userService.register(request);
    }


    @GetMapping("user/info")
    @Operation(summary = "根据id获取用户详细信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    public UserResponse getUserDetails(int userId) {
        return userService.getUserInfo(userId);
    }
    @GetMapping("user/info-by-email")
    @Operation(summary = "根据id获取用户详细信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    public UserResponse getUserInfoByEmail(String email) {
        return userService.getUserInfoByEmail(email);
    }

    @GetMapping("user")
    @Operation(summary = "查询当前登录用户", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
    })
    public UserResponse queryCurrentUser(){
        if (StpUtil.isLogin()){
            return userService.getUserInfo(StpUtil.getLoginIdAsInt());
        }else{
            return new UserResponse(APIResponse.notLoggedIn());
        }
    }

    @PatchMapping("user/info")
    @Operation(summary = "更新当前登录用户信息", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = QueryResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QueryResponse.class))),
    })
    APIResponse updateUserInfo(RegisterRequest request){
        if (!StpUtil.isLogin()){
            return APIResponse.notLoggedIn();
        }
        return userService.updateUserInfo(StpUtil.getLoginIdAsInt(),request);
    }

    @DeleteMapping("user/delete")
    @Operation(summary = "删除用户", responses = {
            @ApiResponse(responseCode = "200", description = "成功调用方法",
                    content = @Content(mediaType ="application/json",schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "访问失败",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
    })
    APIResponse deleteUser(int userId){
        if (!StpUtil.isLogin()){
            return APIResponse.notLoggedIn();
        }
        if (StpUtil.getLoginIdAsInt()!=userId){
            return APIResponse.fail("无权操作");
        }
        return userService.deleteUser(userId);
    }
}
