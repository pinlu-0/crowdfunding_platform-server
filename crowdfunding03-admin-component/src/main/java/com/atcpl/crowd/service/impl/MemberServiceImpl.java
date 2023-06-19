package com.atcpl.crowd.service.impl;

import com.atcpl.crowd.entity.TMember;
import com.atcpl.crowd.mapper.TMemberMapper;
import com.atcpl.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.impl
 * @ClassName：MemberServiceImpl
 * @Date：2023/4/18 18:30
 * @Version：1.0.0
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    TMemberMapper tMemberMapper;


    @Override
    public TMember selectMemberInfoByTicket(String id) {
        return tMemberMapper.selectMemberInfoByTicket(id);
    }


    @Override
    public Integer updateAuthStatus(TMember tMember) {
        int i = tMemberMapper.updateByPrimaryKeySelective(tMember);
        return i;
    }
}
