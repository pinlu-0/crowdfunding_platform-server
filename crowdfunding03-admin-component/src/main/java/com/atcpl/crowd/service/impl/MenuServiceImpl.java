package com.atcpl.crowd.service.impl;

import com.atcpl.crowd.entity.Menu;
import com.atcpl.crowd.entity.MenuExample;
import com.atcpl.crowd.entity.Role;
import com.atcpl.crowd.mapper.MenuMapper;
import com.atcpl.crowd.mapper.RoleMapper;
import com.atcpl.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cpl
 * @date 2022/11/3
 * @apiNote
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveTreeNode(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateTreeNode(Menu menu) {
        // 由于pid没有传入 所以使用有选择的更新，保证pid不会被置空
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeTreeNode(Integer nodeId) {
        menuMapper.deleteByPrimaryKey(nodeId);
    }

    @Override
    public List<Menu> getAllMenu() {
        List<Menu> list = menuMapper.getAllMenu();
        // 保存父菜单
        List<Menu> menus = new ArrayList<>();

        // 保存子菜单
        Map<Integer, Menu> map = new HashMap<>();


        for (Menu menu : list) {
            map.put(menu.getId(), menu);
        }

        for (Menu menu : list) {
            if (menu.getPid() == null) {
                // 说明这个是顶级父菜单,将其加入父菜单集合中
                menus.add(menu);
            } else {
                // 子菜单
                Integer pid = menu.getPid();
                Menu p_menu = map.get(pid);
                List<Menu> children = p_menu.getChildren();
                if (children != null) {
                    children.add(menu);
                } else {
                    children = new ArrayList<>();
                    children.add(menu);
                    p_menu.setChildren(children);
                }
            }
        }
        return menus;
    }

    @Override
    public List<Menu> getRoleMenuByRoleId(Integer roleId) {
        return menuMapper.getRoleMenuByRoleId(roleId);
    }

    @Override
    public boolean assignRoleMenu(String mids, Integer rid) {
        // 1.删除当前角色所有的菜单权限
        roleMapper.deleteAllMenuByRoleId(rid);
        // 2.新增
        String[] split = mids.split(",");
        if (split != null && split.length > 1) {
            for (String mid : split) {
                int i = Integer.parseInt(mid);
                Role role = new Role();
                // 给角色设置角色id
                role.setId(rid);
                // 给角色设置菜单权限id
                role.setMenuId(i);
                // 保存
                roleMapper.assignMenu(role);
            }
            return true;
        }
        return false;
    }

}
