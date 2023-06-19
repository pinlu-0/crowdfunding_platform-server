package com.atcpl.crowd.service.impl;

import com.atcpl.crowd.entity.Auth;
import com.atcpl.crowd.entity.AuthExample;
import com.atcpl.crowd.mapper.AuthMapper;
import com.atcpl.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author cpl
 * @date 2022/11/10
 * @apiNote
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAllAuth() {
        AuthExample authExample = new AuthExample();
        List<Auth> authList = authMapper.selectByExample(authExample);
        return authList;
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void doAssignAuth(Map<String, List<Integer>> map) {
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);

        // 先删除已有的权限
        authMapper.deleteOldRelationShip(roleId);

        // 再添加要添加的新的权限
        List<Integer> authIdList = map.get("authIdArray");

        if (authIdList != null && authIdList.size() > 0) {
            authMapper.insertNewRelationShip(roleId,authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }
}
