package com.tongji.microservice.teamsphere.userservice.impl;

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

import static com.tongji.microservice.teamsphere.dto.APIResponse.*;

@DubboService
public class UserServiceImpl implements UserService {
    @DubboReference(check = false)
    private ProjectService projectService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public APIResponse updateUserInfo(int userId, RegisterRequest request) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        if (userMapper.selectOne(queryWrapper) == null)
            return fail("用户不存在");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userId);
        updateWrapper.set("username", request.getUsername());
        updateWrapper.set("password", request.getPassword());
        updateWrapper.set("email", request.getEmail());
        updateWrapper.set("avatar", request.getAvatar());
        userMapper.update(updateWrapper);
        return success();
    }

    @Override
    public UserResponse getUserInfo(int userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null)
            return new UserResponse(fail("用户不存在"));
        return new UserResponse(user.id, user.username, user.email, user.avatar);
    }

    @Override
    public APIResponse deleteUser(int userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        if (userMapper.selectOne(queryWrapper) == null)
            return fail("用户不存在");
        userMapper.delete(queryWrapper);
        return success();
    }

    @Override
    public UserResponse getUserInfoByEmail(String email) {
        try {
            var user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
            if (user == null) {
                return new UserResponse(fail("用户不存在"));
            }
            return new UserResponse(
                    user.id, user.username, user.email, user.avatar
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new UserResponse(fail("查询失败"));
        }

    }


    @Override
    public LoginResponse checkID(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return new LoginResponse(fail("无效用户名"));
        } else if (!user.password.equals(password)) {
            return new LoginResponse(fail("错误的密码"));
        } else {
            return new LoginResponse(success(), user.getId());
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        var user = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getAvatar());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername());
        System.out.printf("username:%s", request.getUsername());
        if (userMapper.selectOne(queryWrapper) != null) {
            return new RegisterResponse(fail("用户名已存在"));
        }
        var flag = userMapper.insert(user);
        if (flag == 0) {
            return new RegisterResponse(fail("数据库访问失败"));
        } else {
            //访问数据库生成id
            user = userMapper.selectOne(queryWrapper);
            if (user == null) {
                return new RegisterResponse(fail("数据库查询失败"));
            }
            return new RegisterResponse(success(), user.id, user.username);
        }
    }

    @Override
    public AuthorizeResponse authorize(String token) {
        DecodedJWT x;
        try {
            x = Jwt.getVerifier().verify(token);
        } catch (JWTVerificationException e) {
            return new AuthorizeResponse(APIResponse.fakeToken());
        }
        return new AuthorizeResponse(success(), x.getClaim("userid").asInt());
    }
}
