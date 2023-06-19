package com.atcpl.crowd.mvc.config;

import com.atcpl.crowd.constant.CrowdConstant;
import com.atcpl.crowd.entity.Admin;
import com.atcpl.crowd.entity.Role;
import com.atcpl.crowd.service.api.AdminService;
import com.atcpl.crowd.service.api.AuthService;
import com.atcpl.crowd.service.api.MenuService;
import com.atcpl.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cpl
 * @date 2022/12/19
 * @apiNote
 */
@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Autowired
    MenuService menuService;


    /**
     * 使用Spring Security实现登录
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据账号名称查询Admin对象
        Admin admin = adminService.getAdminByLoginAcct(username);
        if (admin == null) {
            throw new UsernameNotFoundException(CrowdConstant.MESSAGE_LOGIN_ACCT_NOT_EXISTS);
        }
        // 获取adminId
        Integer adminId = admin.getId();
        // 根据adminId查询角色信息
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 根据adminId查询权限信息
        List<String> assignedAuthNameList = authService.getAssignedAuthNameByAdminId(adminId);
        // 创建结合对象存储角色、权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 遍历assignedRole存入角色信息
        for (Role role : assignedRoleList) {
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        // 遍历assignedAuthNameByAdminId存入权限信息
        for (String authName : assignedAuthNameList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }

        // 查询树形菜单sidebar
//        List<Menu> allMenu = menuService.getAllMenu();
//        admin.setMenuList(allMenu);

        // 封装SecurityAdmin对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
        return securityAdmin;
    }
}
