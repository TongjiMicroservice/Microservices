package com.tongji.microservice.teamsphere.projectservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.*;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.projectservice.entities.Project;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMember;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMemberRequest;
import com.tongji.microservice.teamsphere.projectservice.mapper.ProjectMapper;
import com.tongji.microservice.teamsphere.projectservice.mapper.ProjectMemberMapper;
import com.tongji.microservice.teamsphere.projectservice.mapper.ProjectMemberRequestMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.tongji.microservice.teamsphere.dto.APIResponse.*;

@DubboService
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectMemberMapper memberMapper;
    @Autowired
    private ProjectMemberRequestMapper requestMapper;
    @DubboReference(check = false)
    private UserService userService;

    @Override
    public ProjectIdResponse creatProject(ProjectData projectData) {
        Project project = new Project(projectData);
        //创建项目
        var flag = projectMapper.insert(project);
        if (flag == 0) {
            return new ProjectIdResponse(fail("创建项目失败"), -1);
        }
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("name", project.getName());
        project.setId(projectMapper.selectOne(wrapper).getId());
        //添加超级管理员
        //System.out.printf("flag = %d插入%d\n", flag,project.getLeader());
        memberMapper.insert(new ProjectMember(project.getId(), project.getLeader(), 3));
        return new ProjectIdResponse(success(), project.getId());
    }

    @Override
    public APIResponse addProjectMember(int projectId, int userId) {
        QueryWrapper<ProjectMember> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id", projectId);
        wrapper.eq("user_id", userId);
        var ret = memberMapper.selectOne(wrapper);
        if (ret != null)
            return fail("成员已存在");
        int flat = memberMapper.insert(new ProjectMember(projectId, userId, 1));
        System.out.printf("返回值为%d", flat);
        if (flat == 0) {
            return fail("添加成员失败");
        }
        return success();
    }

    @Override
    public APIResponse updateProjectInfo(int projectId, ProjectData projectData) {
        UpdateWrapper<Project> wrapper = new UpdateWrapper<Project>().eq("id", projectId);
        wrapper.set("name", projectData.getName());
        wrapper.set("description", projectData.getDescription());
        wrapper.set("scale", projectData.getScale());
        wrapper.set("leader", projectData.getLeader());
        var flag = projectMapper.update(wrapper);
        if (flag == 0) {
            return fail("编辑项目失败");
        }
        return success();
    }

    @Override
    public APIResponse removeProjectMember(int projectId, int userId) {
        UpdateWrapper<ProjectMember> wrapper = new UpdateWrapper<>();
        wrapper.eq("project_id", projectId);
        wrapper.eq("user_id", userId);
        var flat = memberMapper.delete(wrapper);
        System.out.printf("返回值为%d", flat);
        if (flat == 0) {
            return fail("移除成员失败");
        }
        return success();
    }

    @Override
    public APIResponse updateProjectMemberPrivilege(int projectId, int userId, int privilege) {
        UpdateWrapper<ProjectMember> wrapper = new UpdateWrapper<>();
        wrapper.eq("project_id", projectId);
        wrapper.eq("user_id", userId);
        wrapper.set("privilege", privilege);
        var flat = memberMapper.update(wrapper);
        if (flat == 0) {
            return fail("变更权限失败");
        }
        return success();
    }

    @Override
    public MembersResponse getProjectMembers(int projectId) {
        var members = memberMapper.getMembers(projectId);
        List<MemberData> memberData = new ArrayList<>();
        for (var member : members) {
            memberData.add(new MemberData(member.getUserId(), member.getProjectId(), member.getPrivilege()));
        }
        return new MembersResponse(memberData);
    }

    @Override
    public ProjectInfoResponse getProjectInfo(int projectId) {
        QueryWrapper<Project> wrapper = new QueryWrapper<Project>();
        wrapper.eq("id", projectId);
        var project = projectMapper.selectOne(wrapper);
        if (project == null)
            return new ProjectInfoResponse(fail("项目不存在"));
        return new ProjectInfoResponse(success(), project.getId(),
                new ProjectData(project.getId(), project.getScale(), project.getName(), project.getDescription(), project.getLeader()));
    }

    @Override
    public APIResponse deleteProject(int projectId) {
        QueryWrapper<Project> wrapper = new QueryWrapper<Project>();
        wrapper.eq("project_id", projectId);
        var project = projectMapper.selectOne(wrapper);
        if (project == null)
            return fail("项目不存在");
        int flat = projectMapper.deleteById(projectId);
        if (flat <= 0)
            return fail("删除失败");
        return success();
    }

    @Override
    public PrivilegeResponse getProjectMemberPrivilege(int projectId, int userId) {
        try {
            return new PrivilegeResponse(memberMapper.getPrivilege(userId, projectId));
        } catch (Exception e) {
            return new PrivilegeResponse(fail("获取失败"));
        }
    }

    @Override
    public ProjectQueryResponse getProjectByUserId(int userId) {
        List<ProjectData> data = new ArrayList<>();
        var list = memberMapper.getProjectByUserId(userId);
        for (var i : list) {
            data.add(projectMapper.selectProjectById(i));
        }
        return new ProjectQueryResponse(data);
    }

    @Override
    public ProjectQueryResponse queryProject() {
        var projects = projectMapper.selectAll();
        List<ProjectData> list = new ArrayList<>();
        for (var project : projects) {
            list.add(new ProjectData(project.getId(), project.getScale(), project.getName(), project.getDescription(), project.getLeader()));
        }
        return new ProjectQueryResponse(list);
    }

    @Override
    public ProjectJoinRequestResponse getProjectJoinRequest(int projectId) {
        var list = requestMapper.getRequestByProjectId(projectId);
        System.out.println("查询完成"+list.size());
        System.out.println(list);
        List<RequestData> dataList = new ArrayList<>();
        for (var req : list) {
            String name = userService.getUserInfo(req.getUserId()).getUsername();
            dataList.add(new RequestData(req.getUserId(), name, req.getRequestTime(), req.getStatus()));
        }
        System.out.println(dataList);
        return new ProjectJoinRequestResponse (dataList);
    }

    @Override
    public APIResponse makeJoinRequest(int userId, int projectId) {
        if(memberMapper.exists(new QueryWrapper<ProjectMember>().eq("user_id", userId).eq("project_id", projectId))){
            return fail("您已加入组织");
        }
        else if(requestMapper.exists(new QueryWrapper<ProjectMemberRequest>().eq("user_id", userId).eq("project_id", projectId))){
            return fail("您已发出加入申请");
        }
        int flat = requestMapper.insert(new ProjectMemberRequest(userId, projectId, LocalDateTime.now(),0));
        if(flat == 0){
            return fail("操作失败");
        }
        return success();
    }

    @Override
    public APIResponse judgeJoinRequest(int userId, int projectId, int judgement){
        try {
            int flat;
            if (judgement == 1) {
                flat = requestMapper.agree(userId, projectId);
                addProjectMember(projectId, userId);
            } else {
                flat = requestMapper.refuse(userId, projectId);
            }
            if(flat == 0){
                return fail("操作失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return fail("发生错误:" + e.getMessage());
        }
        return success();
    }
}
