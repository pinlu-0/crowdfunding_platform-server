<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register</title>
    <%@include file="/WEB-INF/includes/include-css.jsp" %>
    <%@include file="../includes/include-js.jsp" %>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">创意产品众筹平台网</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <form id="registerForm" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户注册</h2>

        <div class="form-group has-success has-feedback">
            <input type="text" id="loginAcct_input" name="loginAcct" class="form-control" placeholder="请输入登录账号"
                   autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" id="userName_input" name="userName" class="form-control" placeholder="请输入用户姓名" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" id="userPassword_input" name="userPswd" class="form-control" placeholder="请输入登录密码"
                   style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="email" id="email_input" name="email" class="form-control" placeholder="请输入邮箱地址"
                   style="margin-top:10px;">
            <span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>

        <div class="form-group has-success has-feedback">
            <input type="text" id="code" name="code" class="form-control" placeholder="请输入验证码"
                   style="margin-top:10px;">
            <span class="glyphicon glyphicon glyphicon-comment form-control-feedback"></span>
        </div>
        <div style="text-align:right;"><a href="admin/to/login/page.html">已有账号，前去登录</a></div>
        <button id="sendEmailBtn" type="button" class="btn btn-lg btn-success btn-block"> 获取验证码</button>
        <button id="regBtn" type="button" class="btn btn-lg btn-success btn-block">注册</button>
    </form>
</div>
<script type="text/javascript">

    $(function () {

        var registerCode ;

        /* 获取验证码 */
        $("#sendEmailBtn").click(function () {

            // 邮箱正则表达式
            var emailPattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            var email = $.trim($("#email_input").val());
            if (email.length == 0 || email == "") {
                layer.msg("邮箱不能为空");
                return false;
            }
            if (!emailPattern.test(email)) {
                layer.msg("邮箱格式不正确");
                return false;
            }
            $.ajax({
                url: "admin/send/register/email.json",
                type: "post",
                data: {email:email},
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    if(data.data.length<6){
                        layer.msg("发送成功");
                        registerCode = data.data;
                        console.log(registerCode);
                    }
                }
            });
        });

        /* 注册用户 */
        $("#regBtn").click(function () {
            var loginAcct = $.trim($("#loginAcct_input").val());
            var userName = $.trim($("#userName_input").val());
            // 用户名正则表达式(必须是汉字)
            var userNamePattern = /^.{2,3}$/;
            var userPswd = $.trim($("#userPassword_input").val());
            // 密码正则表达式 (以字母开头，长度在6~18之间，只能包含字母、数字和下划线)
            var passwordPattern = /^[a-zA-Z]\w{5,17}$/;
            // 邮箱正则表达式
            var emailPattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            var email = $.trim($("#email_input").val());

            // 验证码
            var code = $.trim($("#code").val());

            if (loginAcct.length == 0 || loginAcct == "") {
                layer.msg("登录账号不能为空");
                return false;
            }

            if (userName.length == 0 || userName == "") {
                layer.msg("用户名不能为空");
                return false;
            }

            if (userPswd.length == 0 || userPswd == "") {
                layer.msg("密码不能为空");
                return false;
            }

            if (email.length == 0 || email == "") {
                layer.msg("邮箱不能为空");
                return false;
            }
            if (!emailPattern.test(email)) {
                layer.msg("邮箱格式不正确");
                return false;
            }

            // if (!passwordPattern.test(userPswd)) {
            //     layer.msg("密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线");
            //     return false;
            // }
            if (!userNamePattern.test(userName)) {
                layer.msg("用户名为3-4位");
                return false;
            }
            if (code.length == 0 || code == "") {
                layer.msg("请输入验证码");
                return false;
            }

            if(registerCode != code){
                layer.msg("验证码错误");
                return false;
            }


            var data = {
                "loginAcct": loginAcct,
                "userName": userName,
                "userPswd": userPswd,
                "email": email
            }
            var requestBody = JSON.stringify(data);
            $.ajax({
                "url": "/admin/save.json",
                "type": "post",
                "data": requestBody,
                "async": false,
                "contentType": "application/json",
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("注册成功");
                        window.location.href = "admin/to/login/page.html";
                    }
                    if (result == "FAILED") {
                        layer.msg("注册失败");
                    }
                },
                "error": function (response) {
                    layer.msg(response.statusText + " " + response.statusCode());
                }
            });
        });


    });

</script>
</body>
</html>
