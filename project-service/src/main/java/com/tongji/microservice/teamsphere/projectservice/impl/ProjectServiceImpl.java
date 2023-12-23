package com.tongji.microservice.teamsphere.projectservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.MembersResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.ProjectInfoResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.entities.projectservice.ProjectData;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ProjectServiceImpl implements ProjectService {
    @Override
    public APIResponse creatProject(String token, ProjectData projectData) {
        return null;
    }

    @Override
    public APIResponse addProjectMember(String token, int projectId, int memberId) {
        return null;
    }

    @Override
    public APIResponse updateProjectInfo(String token, int projectId, ProjectData projectData) {
        return null;
    }

    @Override
    public APIResponse removeProjectMember(String token, int projectId, int memberId) {
        return null;
    }

    @Override
    public APIResponse updateProjectMemberPrivilege(String token, int projectId, int memberId, int privilege) {
        return null;
    }

    @Override
    public MembersResponse getProjectMembers(String token, int projectId) {
        return null;
    }

    @Override
    public ProjectInfoResponse getProjectInfo(String token, int projectId) {
        return null;
    }

    @Override
    public APIResponse deleteProject(String token, int projectId) {
        return null;
    }
}
