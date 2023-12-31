package com.atcpl.crowd.mapper;

import com.atcpl.crowd.entity.Menu;
import com.atcpl.crowd.entity.MenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    Menu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> getAllMenu();

    /**
     * @param roleId
     * @return
     * 根据用户id查询当前用户所拥有的菜单权限
     */
    List<Menu> getRoleMenuByRoleId(Integer roleId);
}