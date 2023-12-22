package com.tongji.microservice.teamsphere.projectservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.MembersResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.ProjectInfoResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ProjectServiceImpl implements ProjectService {
    @Override
    public APIResponse creatProject(){
        return new APIResponse();
    };
    @Override
    public APIResponse addProjectMember(String projectId, String memberId){
        return new APIResponse();
    };
    @Override
    public APIResponse removeProjectMember(String projectId, String memberId){
        return new APIResponse();
    };
    @Override
    public APIResponse updateProjectMemberPrivilege(String projectId, String memberId){
        return new APIResponse();
    };
    @Override
    public MembersResponse getProjectMembers(String projectId){
        return null;
    };
    @Override
    public ProjectInfoResponse getProjectInfo(String projectId){
        return null;
    };
}
