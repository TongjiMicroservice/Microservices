package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.MembersResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.ProjectInfoResponse;

public interface ProjectService {
    APIResponse creatProject();
    APIResponse addProjectMember(String projectId, String memberId);
    APIResponse removeProjectMember(String projectId, String memberId);
    APIResponse updateProjectMemberPrivilege(String projectId, String memberId);
    MembersResponse getProjectMembers(String projectId);
    ProjectInfoResponse getProjectInfo(String projectId);
}
