package com.atcpl.crowd.mvc.config;

import com.atcpl.crowd.constant.CrowdConstant;
import com.atcpl.crowd.exception.LoginAcctAlreadyUsedException;
import com.atcpl.crowd.exception.LoginAcctAlreadyUsedForUpdateException;
import com.atcpl.crowd.exception.LoginFailedException;
import com.atcpl.crowd.util.CrowdUtil;
import com.atcpl.crowd.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基于注解的异常映射解析器
 *
 * @author 蜡笔小新
 * @ControllerAdvice :表示当前类是一个基于注解的异常处理器类
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    /**
     * 更新-账号已存在异常
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(LoginAcctAlreadyUsedForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyUsedForUpdateException(LoginAcctAlreadyUsedForUpdateException exception, HttpServletRequest request, HttpServletResponse response) {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 注册-账户已存在异常
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(LoginAcctAlreadyUsedException.class)
    public ModelAndView resolveLoginAcctAlreadyUsedException(LoginAcctAlreadyUsedException exception, HttpServletRequest request, HttpServletResponse response) {
        String viewName = "admin-add";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 登录失败的异常处理
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 访问请求异常
     *
     * @param exception : 实际捕获到的异常类型
     * @param request   ： 当前请求对象
     * @param response  : 当前响应对象
     * @return
     * @ExceptionHandler ：将一个具体的异常类和一个方法关联起来
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView resolveAccessForbiddenException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        // 指定处理完要去的页面
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 公共的异常类型处理方法
     *
     * @param viewName  : 异常处理完成后要去的页面
     * @param exception ：实际捕获到的异常类型
     * @param request   ：当前请求对象
     * @param response  ：当前响应对象
     * @return
     * @throws IOException
     */
    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) {
        // 判断当前请求类型
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);
        // 如果是Ajax请求
        if (judgeRequestType) {
            // 创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            // 创建GSON对象
            Gson gson = new Gson();
            // 将resultEntity对象转为JSON字符串
            String json = gson.toJson(resultEntity);
            try {
                // 将JSON字符串作为响应体返回给浏览器
                response.getWriter().write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 由于使用了原生的Response对象响应浏览器，所以不再提供ModelAndView对象
            return null;
        }
        // 如果程序进入到这个代码块，说明不是Ajax请求
        // 那么就需要返回ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        // 将Exception对象存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

}
