package com.atcpl.crowd.service.impl;

import com.atcpl.crowd.constant.CrowdConstant;
import com.atcpl.crowd.entity.Admin;
import com.atcpl.crowd.entity.AdminExample;
import com.atcpl.crowd.exception.LoginAcctAlreadyUsedException;
import com.atcpl.crowd.exception.LoginAcctAlreadyUsedForUpdateException;
import com.atcpl.crowd.exception.LoginFailedException;
import com.atcpl.crowd.mapper.AdminMapper;
import com.atcpl.crowd.service.api.AdminService;
import com.atcpl.crowd.util.CrowdUtil;
import com.atcpl.crowd.util.ResultEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 蜡笔小新
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveAdmin(Admin admin) {

        // 使用带盐加密
        String encode = bCryptPasswordEncoder.encode(admin.getUserPswd());
        admin.setUserPswd(encode);
        // 2.增加创建日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(date);
        admin.setCreateTime(createTime);
        // 3.执行保存
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            // 如果捕获到添加或者更新有重复的login_acct时抛出DuplicateKeyException异常时，
            if (e instanceof DuplicateKeyException) {
                // 抛出我们自定义的异常
                throw new LoginAcctAlreadyUsedException(CrowdConstant.MESSAGE_LOGIN_ACC_ALREADY_IN_USE);
            }
        }


    }

    @Override
    public List<Admin> getAllAdmin() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1.根据登录账号查询admin对象
        // 创建adminExample对象
        AdminExample adminExample = new AdminExample();
        // 创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // 在criteria中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        // 2.判断admin对象是否为null
        if (admins == null || admins.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        // 3.如果为null就抛出异常
        Admin admin = admins.get(0);
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 4.如果admin存在，将数据库密码从admin中取出
        String userPswdDB = admin.getUserPswd();
        // 5.将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);
        // 6.比较密码 一致返回admin对象，不一致 抛异常
        if (!Objects.equals(userPswdDB, userPswdForm)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 调用PageInfo的startPage方法开启分页功能;充分体现了PageInfo的“非侵入式”设计：不改变原来的任何代码
        PageHelper.startPage(pageNum, pageSize);
        // 执行查询
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);
        return new PageInfo<>(adminList);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public void batchRemove(String ids) {
        String[] split = ids.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : split) {
            int i = 0;
            try {
                i = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            list.add(i);
        }
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andIdIn(list);
        adminMapper.deleteByExample(adminExample);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        return admin;
    }

    @Override
    public void update(Admin admin) {
        // 根据id有选择的更新,对于为null的字段 不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyUsedForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACC_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 由于存在不去除某个用户的全部权限，比如
        // 旧数据：
        // adminId    roleId
        // 1            1(要删除)
        // 1            2(要删除)
        // 1            3
        // 1            4
        // 新数据：
        // adminId    roleId
        // 1            3(本来就有)
        // 1            4(本来就有)
        // 1            5(新增)
        // 1            6(新增)
        // 根据adminId删除旧的关联关系数据
        adminMapper.deleteOldRelationShip(adminId);
        // 根据roleIdList和adminId保存新的关联关系
        if (roleIdList != null && roleIdList.size() > 0) {
            adminMapper.saveNewRelationShip(adminId, roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        Admin admin = admins.get(0);
        return admin;
    }

    @Override
    public boolean sendEmail(String email) {
        // 检查邮箱是否存在
        boolean exist = checkEmailExist(email);

        if (exist) {
            // 因为现在好处与未登录状态，所以session中没有用户id，需要根据邮箱将id查出来
            Admin admin = adminMapper.getAdminIdByEmail(email);
            // 发送邮件 1.生成令牌 2.给数据库保存令牌 3.把带令牌的连接发送给用户
            // 生成令牌
            String token = UUID.randomUUID().toString();
            // 给数据库保存令牌
            adminMapper.createToken(admin.getId(), token);

            // 通过java代码连接上网易的邮局
            // 不要使用SimpleEmail,会出现乱码问题
            HtmlEmail htmlEmail = new HtmlEmail();
            // SimpleEmail email = new SimpleEmail();
            // 这里是SMTP发送服务器的名字：163的如下：
            htmlEmail.setHostName("smtp.163.com");
            //设置用ssl协议发送邮件
            htmlEmail.setSSLOnConnect(true);
            htmlEmail.setSmtpPort(25);
            // 字符编码集的设置
            htmlEmail.setCharset("gbk");
            try {
                // 收件人的邮箱
                htmlEmail.addTo(email);
                // 发送人的邮箱
                htmlEmail.setFrom("15660129782@163.com");
                // 如果需要认证信息的话，设置认证：用户名-授权码。分别为发件人在邮件服务器上的注册名称和设置的授权码，
                htmlEmail.setAuthentication("15660129782@163.com", "AWGENOAUVOEUDGJC");
                htmlEmail.setSubject("密码重置");
                // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
                String url = "http://localhost:8080/admin/to/reset/password.html?token=" + token;
                htmlEmail.setMsg("<a href='" + url + "'>点击重置</a>");
                // 发送
                htmlEmail.send();

            } catch (EmailException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean checkEmailExist(String email) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(email);
        // 保持邮箱唯一性
        List<Admin> list = adminMapper.selectByExample(example);
        if (list != null && list.size() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Admin getAdminByToken(String token) {
        return adminMapper.getAdminByToken(token);
    }

    @Override
    public int deleteToken(String token) {
        return adminMapper.deleteToken(token);
    }

    @Override
    public ResultEntity<String> sendRegisterEmail(String email) {
        // 通过java代码连接上网易的邮局
        HtmlEmail htmlEmail = new HtmlEmail();

        htmlEmail.setHostName("smtp.163.com");
        //设置用ssl协议发送邮件
        htmlEmail.setSSLOnConnect(true);
        htmlEmail.setSmtpPort(25);
        // 字符编码集的设置
        htmlEmail.setCharset("utf-8");
        String code = UUID.randomUUID().toString().replace("-","" ).substring(0,5 );
        try {
            // 收件人的邮箱
            htmlEmail.addTo(email);
            // 发送人的邮箱
            htmlEmail.setFrom("15660129782@163.com");
            // 如果需要认证信息的话，设置认证：用户名-授权码。分别为发件人在邮件服务器上的注册名称和设置的授权码，
            htmlEmail.setAuthentication("15660129782@163.com", "AWGENOAUVOEUDGJC");
            htmlEmail.setSubject("注册验证码");
            htmlEmail.setMsg("<h1>验证码："+code+"</h1>");
            // 发送
            htmlEmail.send();
            return ResultEntity.successWithData(code);
        } catch (EmailException e) {
            e.printStackTrace();
            return ResultEntity.successWithData("验证码发送失败");
        }

    }
}
