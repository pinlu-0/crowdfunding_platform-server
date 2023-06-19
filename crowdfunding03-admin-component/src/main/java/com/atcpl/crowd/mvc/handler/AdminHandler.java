package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.constant.CrowdConstant;
import com.atcpl.crowd.entity.Admin;
import com.atcpl.crowd.mvc.config.WebAppSecurityConfig;
import com.atcpl.crowd.service.api.AdminService;
import com.atcpl.crowd.service.api.MenuService;
import com.atcpl.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Admin控制器
 *
 * @author 蜡笔小新
 */
@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;


    @Autowired
    MenuService menuService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/batchDel.json")
    public ResultEntity<String> batchRemove(@RequestParam("id") String ids) {
        if (!ids.trim().equals("")) {
            adminService.batchRemove(ids);
        }
        return ResultEntity.successWithoutData();
    }

    /**
     * 携带AdminId来到修改页
     *
     * @param adminId
     * @param modelMap
     * @return
     */
    @RequestMapping("admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "admin-edit";
    }

    /**
     * 修改用户信息
     * @param admin 前端传来的admin对象
     * @param pageNum
     * @param keyword
     * @return
     */
    @PreAuthorize("hasRole('总经理助理')")
    @RequestMapping("admin/update.html")
    public String update(Admin admin, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword) {
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 注册用户
     *
     * @param admin
     * @return
     */
    @ResponseBody
    @RequestMapping("admin/save.json")
    public ResultEntity<String> save(@RequestBody Admin admin) {
        adminService.saveAdmin(admin);
        return ResultEntity.successWithoutData();
        //return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    /**
     * 发送注册验证码
     *
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping("admin/send/register/email.json")
    public ResultEntity<String> sendRegisterEmail(String email) {
        ResultEntity<String> registerEmail = adminService.sendRegisterEmail(email);
        return registerEmail;
    }


    /**
     * 删除Admin对象
     *
     * @param adminId
     * @param pageNum
     * @param keyword
     * @return
     */
    @PreAuthorize("hasRole('总监')")
    @RequestMapping("admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId, @PathVariable("pageNum") Integer pageNum, @PathVariable("keyword") String keyword) {
        try {
            adminService.remove(adminId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 模糊查询、分页查询
     *
     * @param keyword  查询的关键词
     * @param pageNum  当前页
     * @param pageSize 每页的数量
     * @param model
     * @return
     */
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, Model model) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        model.addAttribute(CrowdConstant.ATTR_NAME_PAGEINFO, pageInfo);
        model.addAttribute("searchKeyword", keyword);
        return "admin-page";
    }


    /**
     * 退出系统
     *
     * @param session
     * @return
     */
    @RequestMapping("admin/do/logout.html")
    public String doLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    /**
     * 登录（此登陆实现已经被SpringSecurity底层替代 ，具体{@link com.atcpl.crowd.mvc.config.WebAppSecurityConfig.java})
     *
     * @param loginAcct
     * @param userPswd
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd, HttpSession session) {

        // 调用AdminService方法执行登录检查
        // 如果能够返回Admin对象则说明登录成功，如果账号、密码不正确则抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        // 将登录成功返回的Admin对象存入session域中
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

    /**
     * 发送邮件验证找回密码
     *
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/send/email.json")
    public ResultEntity<Boolean> sendEmail(@RequestParam("email") String email) {
        // 判断邮箱是否存在?  存在则发送邮件，提示成功  不存在则不发送
        boolean flag = adminService.sendEmail(email);
        return ResultEntity.successWithData(flag);
    }

    /**
     * 重置密码
     *
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/reset/password.json")
    public ResultEntity<String> resetPassword(@RequestParam("token") String token, Admin admin) {
        // 为了安全需要根据令牌查询当前修改密码的用户
        Admin adminByToken = adminService.getAdminByToken(token);
        // 如果查到了就进行相关操作
        if (adminByToken != null) {
            // 修改密码
            Integer adminId = adminByToken.getId();
            admin.setId(adminId);
            // 对密码进行加密
            String encode = bCryptPasswordEncoder.encode(admin.getUserPswd());
            admin.setUserPswd(encode);
            adminService.update(admin);
        }
        // 删除token
        int i = adminService.deleteToken(token);
        return ResultEntity.successWithoutData();
    }

}
