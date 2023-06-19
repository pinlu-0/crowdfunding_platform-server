package com.atcpl.crowd.service.api;

import com.atcpl.crowd.entity.Admin;
import com.atcpl.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    /**
     * 注册用户
     * @param admin
     */
    void saveAdmin(Admin admin);

    /**
     * 查询所有用户
     * @return
     */
    List<Admin> getAllAdmin();

    /**
     * 根据账户名查询当前账户名所对应的用户是否存在
     * @param loginAcct
     * @param userPswd
     * @return
     */
    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    /**
     * 获取分页信息
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Admin> getPageInfo(String keyword ,Integer pageNum,Integer pageSize );

    /**
     * 根据用户id删除用户
     * @param adminId
     */
    void remove(Integer adminId);

    /**
     * 批量删除
     * @param ids
     */
    void batchRemove(String ids);


    /**
     * 根据用户id查询用户
     * @param adminId
     * @return
     */
    Admin getAdminById(Integer adminId);

    /**
     * 更新用户信息
     * @param admin
     */
    void update(Admin admin);

    /**
     * 保存用户角色关系（给用户分配角色）
     * @param adminId
     * @param roleIdList
     */
    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

    /**
     * 根据账户名获取用户
     * @param username
     * @return
     */
    Admin getAdminByLoginAcct(String username);

    /**
     * 发送验证码以验证修改密码
     * @param email
     * @return
     */
    boolean sendEmail(String email);

    /**
     * 检查邮箱是否存在
     * @param email
     * @return true 存在   false 不存在
     */
    boolean checkEmailExist(String email);

    /**
     * 根据token获取当前修改密码的admin对象
     * @param token
     * @return
     */
    Admin getAdminByToken(String token);

    /**
     * 点击重置按钮后 不论是否重置成功都删除token
     * @param token
     * @return
     */
    int deleteToken(String token);


    /**
     * 发送注册验证码
     * @param email
     * @return
     */
    ResultEntity<String> sendRegisterEmail(String email);
}
