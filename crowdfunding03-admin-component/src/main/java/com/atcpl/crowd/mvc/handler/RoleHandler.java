package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.entity.Menu;
import com.atcpl.crowd.entity.Role;
import com.atcpl.crowd.service.api.MenuService;
import com.atcpl.crowd.service.api.RoleService;
import com.atcpl.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 蜡笔小新
 */
@Controller
public class RoleHandler {

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    /**
     * 批量删除与单条删除
     *
     * @param roleIdList
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/remove/by/role/id/array.json")
    public ResultEntity<String> removeRole(@RequestBody List<Integer> roleIdList) {
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithoutData();
    }

    /**
     * 修改角色信息
     *
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/edit.json")
    public ResultEntity<String> editRole(Role role) {
        roleService.editRole(role);
        return ResultEntity.successWithoutData();
    }

    /**
     * 添加角色信息
     *
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity<String> saveRole(Role role) {
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    /**
     * 在执行添加角色前查询是否已经存在该角色
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/find/exist/role")
    public ResultEntity<Integer> findExistRole(@RequestParam("name") String roleName) {
        int existRole = roleService.findExistRole(roleName);
        return ResultEntity.successWithData(existRole);
    }

    /**
     * 查询Role对象
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        // 封装到ResultEntity对象中返回（如果上面的操作出现异常，交给异常映射机制处理）
        return ResultEntity.successWithData(pageInfo);
    }

    /**
     * 查询出所有的权限
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/get/auths.json")
    public List<Menu> getUnassembleMenus() {
        List<Menu> menus = menuService.getAll();
        return menus;
    }

    /**
     * 根据用户id查询用户拥有的权限
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("/role/get/role/{roleId}")
    public List<Menu> getRoleMenuByRoleId(@PathVariable("roleId") Integer roleId) {
        List<Menu> rolePermission = menuService.getRoleMenuByRoleId(roleId);
        return rolePermission;
    }

    /**
     * 更新角色菜单权限：
     * 1、先删除角色的所有权限
     * 2、在新增之前以及新选中的权限
     *
     * @param mids  菜单id
     * @param rid  角色id
     * @return
     */
    @ResponseBody
    @PreAuthorize("hasRole('总监')")
    @RequestMapping("/role/assign/role/menu/assign")
    public ResultEntity<String> assignRoleMenuRelationship(@RequestParam("mids") String mids, @RequestParam("rid") Integer rid) {
        boolean flag = menuService.assignRoleMenu(mids, rid);
        return ResultEntity.successWithoutData();
    }


}