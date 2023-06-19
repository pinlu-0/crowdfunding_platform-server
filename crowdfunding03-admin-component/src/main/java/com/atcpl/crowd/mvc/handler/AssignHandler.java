package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.entity.Auth;
import com.atcpl.crowd.entity.Role;
import com.atcpl.crowd.service.api.AdminService;
import com.atcpl.crowd.service.api.AuthService;
import com.atcpl.crowd.service.api.RoleService;
import com.atcpl.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author cpl
 * @date 2022/11/9
 * @apiNote 分配资源控制器
 */
@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    /**
     * 执行分配权限
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> doAssignAuth(@RequestBody Map<String, List<Integer>> map) {
        authService.doAssignAuth(map);
        return ResultEntity.successWithoutData();
    }

    /**
     * 来到分配权限的页面
     *
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("/to/assign.html/{roleId}")
    public String toAssignPage(@PathVariable(value = "roleId", required = false) String roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "assign-auth";
    }

    /**
     * 根据roleId查询当前用户的权限有哪些
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdbByRoleId(@RequestParam("roleId") Integer roleId) {
        System.out.println("roleId" + roleId);
        List<Integer> authList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authList);
    }

    /**
     * 查询所有权限信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> authList = authService.getAllAuth();
        return ResultEntity.successWithData(authList);
    }

    /**
     * 给用户分配角色（授权）
     *
     * @param keyword
     * @param pageNum
     * @param adminId
     * @param roleIdList
     * @return
     */
    @PreAuthorize("hasRole('总经理')")
    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("keyword") String keyword, @RequestParam("pageNum") Integer pageNum, @RequestParam("adminId") Integer adminId, @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {
        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }


    /**
     * 带着查到的权限数据来到分配权限页面
     *
     * @return
     */
    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword, Model model) {
        // 查询已分配的
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 查询未分配的
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        // 存入模型
        model.addAttribute("assignedRoleList", assignedRoleList);
        model.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assign-role";
    }

}
