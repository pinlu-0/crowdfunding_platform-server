package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cpl
 * @date 2022/11/3
 * @apiNote
 */
@Repository
public interface MenuService {
    /**
     * 查询所有权限
     * @return
     * @apiNote 在sidebar那里叫菜单，在角色模块中叫权限
     */
    List<Menu> getAll();

    /**
     * 添加菜单结点
     * @param menu
     */
    void saveTreeNode(Menu menu);

    /**
     * 更新菜单结点
     * @param menu
     */
    void updateTreeNode(Menu menu);

    /**
     * 删除菜单结点
     * @param nodeId
     */
    void removeTreeNode(Integer nodeId);


    /**
     * 查询菜单sidebar（组装好的）
     * @return
     * @apiNote 在sidebar那里叫菜单，在角色模块中叫权限
     */
    List<Menu> getAllMenu();

    /**
     * 根据角色Id查询角色拥有权限
     * @param roleId
     * @return
     * @apiNote Menu在sidebar那里叫菜单，在角色模块中叫权限
     */
    List<Menu> getRoleMenuByRoleId(Integer roleId);

    /**
     *
     * @param mids
     * @param rid
     * @return
     */
    boolean assignRoleMenu(String mids, Integer rid);
}
