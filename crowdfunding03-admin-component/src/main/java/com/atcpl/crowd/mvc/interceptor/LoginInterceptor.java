package com.atcpl.crowd.mvc.interceptor;

import com.atcpl.crowd.constant.CrowdConstant;
import com.atcpl.crowd.entity.Admin;
import com.atcpl.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 * @author 蜡笔小新
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 1.通过request对象获取session对象
        HttpSession session = httpServletRequest.getSession();
        // 2.尝试从session域中获取admin对象
        Admin admin = (Admin)session.getAttribute("admin");
        // 3.判断admin对象是否为null
        if(admin == null){
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        }
        // 4.如果admin对象不为空就放行
        return true;
    }
}
