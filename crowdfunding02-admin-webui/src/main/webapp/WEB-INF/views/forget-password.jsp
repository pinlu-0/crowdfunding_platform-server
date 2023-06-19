<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/include-css.jsp" %>
    <link rel="stylesheet" href="/css/login.css">
    <title>忘记密码</title>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">欢迎使用</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <form action="#;" method="POST" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 找回密码</h2>
        <div class="form-group has-success has-feedback">
            <input type="email" name="email" class="form-control" placeholder="请输入邮箱" autofocus>
            <span class="glyphicon glyphicon-envelope form-control-feedback" style="padding-top: 5px;"></span>
        </div>
        <button id="sendEmailBtn" type="button" class="btn btn-lg btn-success btn-block ">发送</button>
    </form>
</div>
</body>
<%-- 引入js文件 --%>
<%@include file="/WEB-INF/includes/include-js.jsp" %>
<script type="text/javascript">
    /* 验证验证码是否合法 */
    $(function () {
        $("#sendEmailBtn").click(function () {
            // 获取验证码
            var email = $("input[name=email]").val().trim();
            if (email.length == 0 && email == "") {
                layer.msg("请输入邮箱");
                return false;
            }
            // 邮箱正则表达式
            var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
            if (!pattern.test(email)) {
                layer.msg("邮箱格式有误");
                return false;
            }
            // 如果邮箱格式正确 就发送请求
            $.ajax({
                "url": "admin/send/email.json",
                "type": "post",
                "data": {email: email},
                "dataType": "json",
                "success": function (response) {
                    if (response.result == "SUCCESS") {
                        layer.msg("验证码发送成功，请查收！");
                    }
                    // location.href="admin/to/reset/password.html";
                }
            });
        });
    });
</script>
</html>