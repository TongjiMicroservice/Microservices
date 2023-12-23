package com.tongji.microservice.teamsphere.userservice.impl;

import com.alibaba.nacos.common.utils.Pair;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.*;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.userservice.entities.User;
import com.tongji.microservice.teamsphere.userservice.mapper.UserMapper;
import com.tongji.microservice.teamsphere.userservice.util.Jwt;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.tongji.microservice.teamsphere.dto.APIResponse.*;

@DubboService
public class UserServiceImpl implements UserService {
    @DubboReference(check = false)
    private ProjectService memberService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public APIResponse updateUserInfo(String token, RegisterRequest request){
        int userId = authorize(token).getUserid();
        if(userId<=0)
            return fakeToken();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        if(userMapper.selectOne(queryWrapper)==null)
            return fail("用户不存在");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userId);
        updateWrapper.set("username", request.getUsername());
        updateWrapper.set("password", request.getPassword());
        updateWrapper.set("email", request.getEmail());
        updateWrapper.set("avatar", request.getAvatar());
        userMapper.update(updateWrapper);
        return success();
    };

    @Override
    public UserResponse getUserInfo(int userId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null)
            return new UserResponse(fail("用户不存在"));
        return new UserResponse(success(), user.id,user.username, user.email, user.avatar);
    }

    @Override
    public QueryResponse queryUser(String token, UserQueryRequest request) {
        if(authorize(token).getUserid()<=0)
            return new QueryResponse(fakeToken());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "username");
        if(request.getUserid()>0){
            queryWrapper.eq("id", request.getUserid());
        } else if (request.getUsername()!=null) {
            queryWrapper.eq("username", request.getUsername());
        }else if(request.getEmail()!=null)
            queryWrapper.eq("email", request.getEmail());
        else
            return new QueryResponse(fail("查询条件不能为空"));
        List<User> users = userMapper.selectList(queryWrapper);
        List<UserData> list= new ArrayList<>();
        for(var user:users){
            list.add(new UserData(user.id, user.username));
        }
        return new QueryResponse(success(),list);
    }

    @Override
    public APIResponse deleteUser(String token, int userId){
        if(authorize(token).getUserid() != userId)
            return fail("权限不足，非本人无法删除用户");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        if(userMapper.selectOne(queryWrapper)==null)
            return fail("用户不存在");
        userMapper.delete(queryWrapper);
        return success();
    };


    @Override
    public LoginResponse login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return new LoginResponse(fail("无效用户名"));
        } else if (!user.password.equals(password)) {
            return new LoginResponse(fail( "错误的密码"));
        } else {
            String token = Jwt.generateToken(user.id,3600*24*30);
            return new LoginResponse(success(), user.id, token);
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        var user=new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getAvatar());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername());
        if(userMapper.selectOne(queryWrapper)!=null){
            return new RegisterResponse(fail("用户名已存在"));
        }
        var flag=userMapper.insert(user);
        if(flag==0){
            return new RegisterResponse(fail("数据库访问失败"));
        }
        else{
            //访问数据库生成id
            user=userMapper.selectOne(queryWrapper);
            if(user==null){
                return new RegisterResponse(fail("数据库查询失败"));
            }
            String token = Jwt.generateToken(user.id,1000);
            return new RegisterResponse(success(),user.id,user.username);
        }
    }

    @Override
    public AuthorizeResponse authorize(String token) {
        DecodedJWT x;
        try {
            x = Jwt.getVerifier().verify(token);
        }catch (JWTVerificationException e){
            return new AuthorizeResponse(APIResponse.fakeToken());
        }
        return new AuthorizeResponse(success(),x.getClaim("userid").asInt());
    }
}
