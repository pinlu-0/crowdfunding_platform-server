package com.atcpl.crowd.mvc.config;

import com.atcpl.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author cpl
 * @date 2022/12/18
 * @apiNote
 * @Configuration 配置类
 * @EnableWebSecurity 启用Web环境下权限验证
 * @EnableGlobalMethodSecurity(prePostEnabled = true) 启用全局方法权限控制功能，并且设置@PreAuthorize()、@PostAuthorize()、@PreFilter()、@PostFilter()生效
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 身份认证接口
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * 接口API拦截
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 授权
        http.authorizeRequests() // 对请求授权
                // 针对登录页进行设置
                .antMatchers("/admin/to/login/page.html").permitAll()
                .antMatchers("/admin/to/register/page.html").permitAll()
                .antMatchers("/admin/to/forget/password.html").permitAll()
                .antMatchers("/admin/send/email.json").permitAll()
                .antMatchers("/admin/send/register/email.json").permitAll()
                .antMatchers("/admin/to/reset/password.html").permitAll()
                .antMatchers("/admin/reset/password.json").permitAll()
                .antMatchers("/admin/save.json").permitAll()
                .antMatchers("/bootstrap/**", "/css/**", "/fonts/**", "/img/**", "/jquery/**", "/js/**", "/layer-v3.1.1/**", "/script/**", "/ztree/**")
                .permitAll()
                .anyRequest()
                .authenticated(); // 认证后访问

        // 异常处理
        http.exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        request.setAttribute(CrowdConstant.ATTR_NAME_EXCEPTION, new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/views/system-error.jsp").forward(request, response);
                    }
                });

        // 使用SpringSecurity的表单登录
        http.formLogin() // 开启表单登录功能呢
                // 指定自定义登录页面
                .loginPage("/admin/to/login/page.html")
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .permitAll()
                // 登录成功重定向的地址
                .defaultSuccessUrl("/admin/to/main/page.html")
                // 登录处理URL(由SpringSecurity框架处理)
                // 自定义登录逻辑
                .loginProcessingUrl("/admin/do/login.html");

        // 禁用csrf跨站请求伪造
        http.csrf()
                .disable();

        // 退出登录
        http.logout()
                .logoutUrl("/security/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html");


        // 配置session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }
}
