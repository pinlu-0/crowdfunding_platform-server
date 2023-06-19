package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author cpl
 * @date 2022/11/10
 * @apiNote
 */
public interface AuthService {

    List<Auth> getAllAuth();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void doAssignAuth(Map<String, List<Integer>> map);

    // 查询已分配的权限
    List<String> getAssignedAuthNameByAdminId(Integer adminId);


}
