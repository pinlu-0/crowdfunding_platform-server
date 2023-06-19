<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/include-css.jsp" %>
    <link rel="stylesheet" href="/css/login.css">
    <title>登录</title>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="javascript:void(null);">众筹平台 ${navinfo}</a>
            </div>
        </div>
    </div>
</nav>

<div class="container">
    <%-- security/do/login.html --%>
    <%-- admin/do/login.html --%>
    <form action="admin/do/login.html" method="POST" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 管理员登录</h2>
        <p>${requestScope.exception.message}</p>
        <p>${SPRING_SECURITY_LAST_EXCEPTION}</p>
        <div class="form-group has-success has-feedback">
            <input type="text" name="loginAcct" class="form-control" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback" style="padding-top: 5px;"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" name="userPswd" class="form-control" placeholder="请输入登录密码">
            <span class="glyphicon glyphicon-lock form-control-feedback" style="padding-top: 5px;"></span>
        </div>

        <div class="col-md-4"><input type="checkbox" name="remember_me" value="1"> 记住我</div>
        <div style="text-align:right;">
            <a href="admin/to/register/page.html">还没有账号，点我注册</a>
        </div>
        <div style="text-align:right;"><a href="admin/to/forget/password.html">忘记密码</a></div>
        <button type="submit" class="btn btn-lg btn-success btn-block">登录</button>
    </form>
</div>
</body>


<%@include file="/WEB-INF/includes/include-js.jsp" %>

</html>