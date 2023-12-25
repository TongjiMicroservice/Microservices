package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.MembersResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.PrivilegeResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.ProjectInfoResponse;
import com.tongji.microservice.teamsphere.entities.projectservice.ProjectData;

public interface ProjectService {
    APIResponse creatProject(ProjectData projectData);
    APIResponse addProjectMember(int projectId, int userId);

    APIResponse updateProjectInfo(int projectId, ProjectData projectData);
    APIResponse removeProjectMember(int projectId, int userId);
    APIResponse updateProjectMemberPrivilege(int projectId, int userId, int privilege);
    MembersResponse getProjectMembers(int projectId);
    ProjectInfoResponse getProjectInfo(int projectId);
    APIResponse deleteProject(int projectId);
    PrivilegeResponse getProjectMemberPrivilege(int projectId, int userId);
}
