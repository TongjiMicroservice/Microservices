package com.tongji.microservice.teamsphere.memberservice.impl;

import com.tongji.microservice.teamsphere.dubbo.api.MemberService;
import com.tongji.microservice.teamsphere.entities.memberservice.Member;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

@DubboService
public class MemberServiceImpl implements MemberService {
    @Override
    public void creatProject(){};
    @Override
    public void addProjectMember(String projectId, String memberId){};
    @Override
    public void removeProjectMember(String projectId, String memberId){};
    @Override
    public void updateProjectMemberPrivilege(String projectId, String memberId){};
    @Override
    public List<Member> getProjectMembers(String projectId){
        return null;
    };
    @Override
    public List<Member> getProjectInfo(String projectId){
        return null;
    };
}
