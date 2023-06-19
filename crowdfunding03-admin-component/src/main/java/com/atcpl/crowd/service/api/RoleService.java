package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    /**
     * 查询角色信息并分页
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    /**
     * 保存角色
     * @param role
     */
    void saveRole(Role role);

    /**
     * 编辑角色
     * @param role
     */
    void editRole(Role role);

    /**
     * 删除角色
     * @param roleIdList
     */
    void removeRole(List<Integer> roleIdList);

    /**
     * 查询已分配的角色
     * @param adminId
     * @return
     */
    List<Role> getAssignedRole(Integer adminId);

    /**
     * 查询未分配的角色
     * @param adminId
     * @return
     */
    List<Role> getUnAssignedRole(Integer adminId);

    /**
     * 在执行添加角色前查重角色
     * @param roleName
     * @return
     */
    int findExistRole(String roleName);
}
