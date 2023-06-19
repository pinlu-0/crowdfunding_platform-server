<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%-- 使用公共的问件头 --%>
<%@include file="../includes/include-css.jsp" %>

<body>
<% pageContext.setAttribute("navinfo", "- 异常信息");%>

<%-- 公共的导航栏 --%>
<%@include file="/WEB-INF/includes/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <h2 class="form-signin-heading" style="text-align: center">
            <i class="glyphicon glyphicon-log-in"></i> 系统信息
        </h2>
        <h3 style="text-align: center">${requestScope.exception.message}</h3>
        <button style="width: 150px;margin: 50px auto 0px auto " class="btn btn-lg btn-success btn-block">点我返回</button>
    </div>
</div>
</body>
<%-- JS代码 --%>
<%-- 引入公共的JS文件 --%>
<%@include file="/WEB-INF/includes/include-js.jsp" %>
<script type="text/javascript">
    $(function () {
        $("button").click(function () {
            // 返回上一步
            window.history.back();
        });
    });
</script>
</html>
