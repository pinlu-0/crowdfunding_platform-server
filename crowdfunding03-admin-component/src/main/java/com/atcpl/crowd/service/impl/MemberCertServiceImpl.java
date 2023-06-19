package com.atcpl.crowd.service.impl;

import com.atcpl.crowd.entity.TMemberCert;
import com.atcpl.crowd.mapper.TMemberCertMapper;
import com.atcpl.crowd.service.api.MemberCertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.impl
 * @ClassName：MemberCertServiceImpl
 * @Date：2023/4/18 18:54
 * @Version：1.0.0
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Service
public class MemberCertServiceImpl implements MemberCertService {


    @Autowired
    TMemberCertMapper tMemberCertMapper;

    @Override
    public List<TMemberCert> selectCertsByTicketId(String processInstanceId) {

        return tMemberCertMapper.selectCertsByTicketId(processInstanceId);
    }
}
