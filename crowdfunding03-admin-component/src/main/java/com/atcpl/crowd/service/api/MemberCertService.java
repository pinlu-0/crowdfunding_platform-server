package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.TMemberCert;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.service.api
 * @ClassName：MemberCertService
 * @Date：2023/4/18 18:54
 * @Version：1.0.0
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface MemberCertService {
    List<TMemberCert> selectCertsByTicketId(String processInstanceId);
}
