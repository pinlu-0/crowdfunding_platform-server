package com.atcpl.crowd.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author cpl
 * @date 2023/1/5
 * @apiNote TODO（监控zuul网关允许访问的资源）
 */
public class AccessPermitThroughResources {
    /**
     * 存放允许通过的请求资源
     */
    public static final Set<String> PERMIT_THROUGH_REQUEST_RESOURCES_SET = new HashSet<>();

    /**
     * 请求资源静态代码块
     */
    static {
        // 首页
        PERMIT_THROUGH_REQUEST_RESOURCES_SET.add("/");
        // 注册页
        PERMIT_THROUGH_REQUEST_RESOURCES_SET.add("/fg/auth/to/register/page.html");
        // 登录页
        PERMIT_THROUGH_REQUEST_RESOURCES_SET.add("/fg/auth/to/login/page.html");
        // 执行登录
        PERMIT_THROUGH_REQUEST_RESOURCES_SET.add("/fg/auth/do/member/login.json");
        // 退出登录
        PERMIT_THROUGH_REQUEST_RESOURCES_SET.add("/fg/auth/exit/to/login/page.html");
        // 执行注册
        PERMIT_THROUGH_REQUEST_RESOURCES_SET.add("/fg/auth/do/member/register.json");
        // 发送短信
        PERMIT_THROUGH_REQUEST_RESOURCES_SET.add("/fg/auth/send/short/message.json");
    }

    /**
     * 存放允许通过的静态资源
     */
    public static final Set<String> PERMIT_THROUGH_STATIC_RESOURCES_SET = new HashSet<>();

    /**
     * 静态资源静态代码块
     */
    static {
        PERMIT_THROUGH_STATIC_RESOURCES_SET.add("bootstrap");
        PERMIT_THROUGH_STATIC_RESOURCES_SET.add("css");
        PERMIT_THROUGH_STATIC_RESOURCES_SET.add("fonts");
        PERMIT_THROUGH_STATIC_RESOURCES_SET.add("img");
        PERMIT_THROUGH_STATIC_RESOURCES_SET.add("jquery");
        PERMIT_THROUGH_STATIC_RESOURCES_SET.add("layer-v3.1.1");
        PERMIT_THROUGH_STATIC_RESOURCES_SET.add("script");
        PERMIT_THROUGH_STATIC_RESOURCES_SET.add("ztree");
    }

    /**
     * 判断某个ServletPath值是否对应一个静态资源
     * @param servletPath
     * @return true：是静态资源   false：不是静态资源
     */
    public static boolean judgeCurrentServletPathWhetherStaticResource(String servletPath) {
        if (servletPath == null || servletPath.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        // 按”/"进行切分
        String[] split = servletPath.split("/");
        // 分割后得到一个 ["","aaa","bbb","ccc"]这样的数组，所有下标从1开始拿
        String firstLevelPathVal = split[1];
        // 判断静态资源是否包含下标为1的值
        return PERMIT_THROUGH_STATIC_RESOURCES_SET.contains(firstLevelPathVal);
    }

}
