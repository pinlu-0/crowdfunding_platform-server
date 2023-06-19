package com.atcpl.crowd.mvc.config;

import com.atcpl.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 考虑到User对象中仅仅包含账户、密码，为了能够获取到原始的Admin对象，专门创建这个类对User扩展
 * @author cpl
 * @date 2022/12/19
 * @apiNote
 */
public class SecurityAdmin extends User {

    /**
     * 原始的Admin对象，包含Admin对象的全部属性
     */
    private Admin originalAdmin;

    /**
     *
     * @param originalAdmin : 传入原始的Admin对象
     * @param authorities : 创建角色、权限信息的集合
     */
    public SecurityAdmin(Admin originalAdmin, Collection<? extends GrantedAuthority> authorities) {
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);
        // 给本类的originalAdmin赋值
        this.originalAdmin = originalAdmin;
        // 擦除原始Admin对象中userPswd对象的值
        this.originalAdmin.setUserPswd(null);
    }

    /**
     * 获取原始的Admin对象的get方法
     * @return
     */
    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

    public void setOriginalAdmin(Admin originalAdmin) {
        this.originalAdmin = originalAdmin;
    }
}
