package com.tongji.microservice.teamsphere.dubbo.api;

import com.tongji.microservice.teamsphere.entities.memberservice.Member;

import java.util.List;

public interface MemberService {
    void addProjectMember(String projectId, String memberId);
    void removeProjectMember(String projectId, String memberId);
    void assignProjectAdmin(String projectId, String memberId);
    void removeProjectAdmin(String projectId, String memberId);
    List<Member> getProjectMembers(String projectId);
}
