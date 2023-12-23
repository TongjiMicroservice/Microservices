package com.tongji.microservice.teamsphere.projectservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.MembersResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.ProjectInfoResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.entities.projectservice.ProjectData;
import com.tongji.microservice.teamsphere.projectservice.entities.Project;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMember;
import com.tongji.microservice.teamsphere.projectservice.mapper.ProjectMapper;
import com.tongji.microservice.teamsphere.projectservice.mapper.ProjectMemberMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tongji.microservice.teamsphere.dto.APIResponse.*;

@DubboService
public class ProjectServiceImpl implements ProjectService {
    @DubboReference(check = false)
    private UserService userService;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectMemberMapper memberMapper;
    @Override
    public APIResponse creatProject(String token, ProjectData projectData)  {
        int userId = userService.authorize(token).getUserid();
        if(userId <= 0)
            return fakeToken();
        Project project = new Project(projectData);
        //创建项目
        var flag = projectMapper.insert(project);
        if(flag == 0){
            return fail("创建项目失败");
        }
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("name",project.getName());
        project.setId(projectMapper.selectOne(wrapper).getId());
        //添加超级管理员
        //System.out.printf("flag = %d插入%d\n", flag,project.getLeader());
        memberMapper.insert(new ProjectMember(project.getId(),project.getLeader(), 3));
        return success();
    }

    @Override
    public APIResponse addProjectMember(String token, int projectId, int userId) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0){
            return fakeToken();
        }
        System.out.printf("projectId = %d,userId = %d\n",projectId,adminId);
        int privilege = memberMapper.getPrivilege(adminId, projectId);
        if(privilege <= 1){
            return fail("没有权限");
        }
        var flat = memberMapper.insert(new ProjectMember(projectId,userId,1));
        System.out.printf("返回值为%d",flat);
        if(flat == 0){
            return fail("添加成员失败");
        }
        return success();
    }

    @Override
    public APIResponse updateProjectInfo(String token, int projectId, ProjectData projectData) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0)
            return fakeToken();
        int privilege = memberMapper.getPrivilege(adminId, projectId);
        if(privilege <= 1)
            return fail("没有权限");
        UpdateWrapper<Project> wrapper = new UpdateWrapper<Project>().eq("id",projectId);
        wrapper.set("name",projectData.getName());
        wrapper.set("description",projectData.getDescription());
        wrapper.set("scale",projectData.getScale());
        wrapper.set("leader",projectData.getLeader());
        var flag = projectMapper.update(wrapper);
        if(flag == 0){
                return fail("编辑项目失败");
        }
        return success();
    }

    @Override
    public APIResponse removeProjectMember(String token, int projectId, int userId) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0)
            return fakeToken();
        int privilege = memberMapper.getPrivilege(adminId, projectId);
        if(privilege <= 1)
            return fail("没有权限");
        UpdateWrapper<ProjectMember> wrapper = new UpdateWrapper<>();
        wrapper.eq("project_id",projectId);
        wrapper.eq("user_id",userId);
        var flat = memberMapper.delete(wrapper);
        System.out.printf("返回值为%d",flat);
        if(flat == 0){
            return fail("移除成员失败");
        }
        return success();
    }

    @Override
    public APIResponse updateProjectMemberPrivilege(String token, int projectId, int userId, int privilege) {
        int adminId = userService.authorize(token).getUserid();
        if(adminId <= 0)
            return fakeToken();
        int adminPrivilege = memberMapper.getPrivilege(adminId, projectId);
        if(adminPrivilege <= 1)
            return fail("没有权限");
        UpdateWrapper<ProjectMember> wrapper = new UpdateWrapper<>();
        wrapper.eq("project_id",projectId);
        wrapper.eq("user_id",userId);
        wrapper.set("privilege",privilege);
        var flat = memberMapper.update(wrapper);
        System.out.printf("返回值为%d",flat);
        if(flat == 0){
            return fail("变更权限失败");
        }
        return success();
    }

    @Override
    public MembersResponse getProjectMembers(String token, int projectId) {

        return null;
    }

    @Override
    public ProjectInfoResponse getProjectInfo(String token, int projectId) {
        int userId = userService.authorize(token).getUserid();
        if(userId <= 0)
            return new ProjectInfoResponse(fakeToken());

        return null;
    }

    @Override
    public APIResponse deleteProject(String token, int projectId) {
        return null;
    }
}
