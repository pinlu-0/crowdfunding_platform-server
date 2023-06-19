package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.TMember;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.api
 * @ClassName：MemberService
 * @Date：2023/4/18 18:29
 * @Version：1.0.0
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface MemberService {
    /**
     * 根据工单查询基本信息
     * @param id
     * @return
     */
    TMember selectMemberInfoByTicket(String id);

    /**
     * 修改认证状态
     * @param tMember
     * @return
     */
    Integer updateAuthStatus(TMember tMember);
}
