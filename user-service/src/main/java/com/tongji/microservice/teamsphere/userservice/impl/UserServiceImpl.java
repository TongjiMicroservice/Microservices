package com.tongji.microservice.teamsphere.userservice.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.*;
import com.tongji.microservice.teamsphere.dubbo.api.MemberService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.userservice.entities.User;
import com.tongji.microservice.teamsphere.userservice.mapper.UserMapper;
import com.tongji.microservice.teamsphere.userservice.util.Jwt;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class UserServiceImpl implements UserService {
    @DubboReference(check = false)
    private MemberService memberService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public APIResponse updateUserDetails(UserRequest request){

        return null;
    };

    @Override
    public UserResponse getUserDetails(int userId){
        return null;
    };

    @Override
    public APIResponse deleteUser(int userId){
        return null;
    };


    @Override
    public LoginResponse login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return new LoginResponse(new APIResponse(400, "用户不存在"),0 , null);
        } else if (!user.password.equals(password)) {
            return new LoginResponse(new APIResponse(401, "密码错误"), 0 , null);
        } else {
            String token = Jwt.generateToken(user.id,24*3600);
            return new LoginResponse(new APIResponse(200, "登录成功"), user.id, token);
        }
    }

    @Override
    public RegisterResponse register(UserRequest request) {

        var user=new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getAvatar());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername());
        if(userMapper.selectOne(queryWrapper)!=null){
            return new RegisterResponse(new APIResponse(400,"注册失败:用户名已存在"),-1,null);
        }
        var flag=userMapper.insert(user);
        if(flag==0){
            return new RegisterResponse(new APIResponse(400,"注册失败:数据库访问失败"),-1,null);
        }
        else{
            //访问数据库生成id
            user=userMapper.selectOne(queryWrapper);
            if(user==null){
                return new RegisterResponse(new APIResponse(400,"注册失败:数据库查询失败"),-1,null);
            }
            String token = Jwt.generateToken(user.id,1000);
            return new RegisterResponse(new APIResponse(200,"注册成功"),user.id,user.username);
        }
    }

    @Override
    public AuthorizeResponse authorize(String token) {
        DecodedJWT x;
        try {
            x = Jwt.getVerifier().verify(token);
        }catch (JWTVerificationException e){
            return new AuthorizeResponse(APIResponse.failure(400,"鉴权失败"),0);
        }
        return new AuthorizeResponse(APIResponse.success(),x.getClaim("userid").asInt());
    }
}
