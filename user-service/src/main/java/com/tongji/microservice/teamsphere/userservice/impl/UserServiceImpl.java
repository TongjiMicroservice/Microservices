package com.tongji.microservice.teamsphere.userservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.LoginResponse;
import com.tongji.microservice.teamsphere.dto.userservice.RegisterResponse;
import com.tongji.microservice.teamsphere.dubbo.api.MemberService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.userservice.entities.User;
import com.tongji.microservice.teamsphere.userservice.mapper.UserMapper;
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
    public String createUser(String username, String password) {
        var user=new User(username,password);
        var flag=userMapper.insert(user);
        if(flag==0){
            return "创建失败";
        }
        else{
            return "创建成功";
        }
    }

    @Override
    public void updateUserDetails(String userId, String newUsername, String newEmail){

    };

    @Override
    public User getUserDetails(String userId){
    };

    @Override
    public void deleteUser(String userId){
    };


    @Override
    public LoginResponse login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return new LoginResponse(new APIResponse(400, "用户不存在"), -1, null);
        } else if (!user.password.equals(password)) {
            return new LoginResponse(new APIResponse(401, "密码错误"), -1, null);
        } else {
            return new LoginResponse(new APIResponse(200, "登录成功"), user.id, user.username);
        }
    }

    @Override
    public RegisterResponse register(String username, String password) {
        var user=new User(username, password);
        var flag=userMapper.insert(user);
        if(flag==0){
            return new RegisterResponse(new APIResponse(400,"注册失败"),-1,null);
        }
        else{
            return new RegisterResponse(new APIResponse(200,"注册成功"),user.id,user.username);
        }
    }

    @Override
    public String helloUserService() {
        return "Hello UserService!";
    }

    @Override
    public String callMemberService() {
        return memberService.helloMemberService();
    }
}
