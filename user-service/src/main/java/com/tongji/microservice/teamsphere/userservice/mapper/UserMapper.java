package com.tongji.microservice.teamsphere.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tongji.microservice.teamsphere.userservice.entities.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Select("select max(id) from user")
    int getMaxId()  ;

}

