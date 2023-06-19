<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/include-css.jsp" %>
    <link rel="stylesheet" href="/css/login.css">
    <title>重置密码</title>
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
    <form class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 重置密码</h2>
        <div class="form-group has-success has-feedback">
            <label>新密码：</label>
            <input type="password" id="pswd" name="userPswd" class="form-control" placeholder="请输入新密码" autofocus>
            <span class="glyphicon glyphicon-lock form-control-feedback" style="padding-top: 5px;"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <label>确认新密码：</label>
            <input type="password" id="repswd" name="userPswd" class="form-control" placeholder="请输入新密码" autofocus>
            <span class="glyphicon glyphicon-lock form-control-feedback" style="padding-top: 5px;"></span>
        </div>
        <button id="resetPasswordBtn" type="button" class="btn btn-lg btn-success btn-block ">重置</button>
    </form>
</div>
</body>
<%-- 引入js文件 --%>
<%@include file="/WEB-INF/includes/include-js.jsp" %>
<script type="text/javascript">
    $(function () {


        $("#resetPasswordBtn").click(function () {

            var pswd = $("#pswd").val().trim();

            var repswd = $("#repswd").val().trim();

            if (pswd == "" && pswd.length == 0) {
                layer.msg("请输入新密码");
                return false;
            }
            if (repswd == "" && repswd.length == 0) {
                layer.msg("请输入确认新密码");
                return false;
            }
            if (pswd != repswd) {
                layer.msg("两次输入的密码不相符，请重新输入！");
                return false;
            }

            // 获取url中传递的token
            var url = location.search; //获取url中"?"符后的字串
            var token = "";
            if (url.indexOf("?") != -1) {    //判断是否有参数
                token = url.substr(1).split("="); //从第一个字符开始 因为第0个是?号 获取所有除问号的所有符串
                //strs = str.split("=");   //用等号进行分隔 （因为知道只有一个参数 所以直接用等号进分隔 如果有多个参数 要用&号分隔 再用等号进行分隔）
            }
            // 发送重置密码的请求
            $.ajax({
                "url": "admin/reset/password.json",
                "type": "post",
                "data": {token: token[1],userPswd:repswd},
                "dataType": "json",
                "success": function (response) {
                    if(response.result=="SUCCESS"){
                        layer.msg("重置成功");
                    }
                    location.href="admin/to/login/page.html";
                }
            });

        });
    });
</script>
</html>
