package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.MembersResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.ProjectInfoResponse;
import com.tongji.microservice.teamsphere.entities.projectservice.ProjectData;

public interface ProjectService {
    APIResponse creatProject(String token, ProjectData projectData);
    APIResponse addProjectMember(String token, int projectId, int memberId);

    APIResponse updateProjectInfo(String token, int projectId, ProjectData projectData);
    APIResponse removeProjectMember(String token, int projectId, int memberId);
    APIResponse updateProjectMemberPrivilege(String token, int projectId, int memberId, int privilege);
    MembersResponse getProjectMembers(String token, int projectId);
    ProjectInfoResponse getProjectInfo(String token, int projectId);
    APIResponse deleteProject(String token, int projectId);
}
