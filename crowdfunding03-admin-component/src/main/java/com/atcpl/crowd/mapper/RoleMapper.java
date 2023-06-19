package com.atcpl.crowd.mapper;

import com.atcpl.crowd.entity.Role;
import com.atcpl.crowd.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    List<Role> selectRoleByKeyword(String keyword);

    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);

    /**
     * 根据角色id删除所有菜单权限
     * @param rid
     */
    void deleteAllMenuByRoleId(Integer rid);

    /**
     * 执行分配
     * @param role
     */
    void assignMenu(Role role);
}