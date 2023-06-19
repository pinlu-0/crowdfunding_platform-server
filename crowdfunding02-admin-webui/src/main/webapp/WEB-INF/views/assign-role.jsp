<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<title>角色分配</title>
<%-- 使用公共的问价头 --%>
<%@include file="../includes/include-css.jsp" %>
<%@include file="/WEB-INF/includes/include-js.jsp" %>
<body>
<%pageContext.setAttribute("navinfo", "- 用户维护");%>
<%--公共的导航栏--%>
<%@include file="../includes/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%-- 使用公共的sidebar --%>
        <%@include file="../includes/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="/admin/get/page.html">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>

            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="assign/do/role/assign.html" method="post" role="form" class="form-inline">
                        <input type="hidden" name="adminId" value="${param.adminId}">
                        <input type="hidden" name="pageNum" value="${param.pageNum}">
                        <input type="hidden" name="keyword" value="${param.keyword}">
                        <div class="form-group">
                            <label>未分配角色列表</label><br>
                            <select class="form-control" multiple="" size="10" style="width:200px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label>已分配角色列表</label><br>
                            <select name="roleIdList" class="form-control" multiple="" size="10"
                                    style="width:200px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="submitBtn" type="submit" style="width: 100px;margin: 0 auto;"
                                class="btn btn-lg btn-success btn-block">授权
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(function () {
        $("#toRightBtn").click(function () {
            // 选择器eq()：选择第一个select ; 选择器parent>child ：选择子元素option
            $("select:eq(0)>option:checked").appendTo("select:eq(1)")
        });
        $("#toLeftBtn").click(function () {
            // 选择器eq()：选择第一个select ; 选择器parent>child ：选择子元素option
            $("select:eq(1)>option:checked").appendTo("select:eq(0)")
        });

        $("#submitBtn").click(function () {
            $("select:eq(1)>option").prop("selected", "selected");
        });

    });
    /**
     * 点击某个侧边栏  让其处于展开状态
     */
    $("a[href='admin/get/page.html']").css("color", "red");
    $("a[href='admin/get/page.html']").parents(".list-group-item").removeClass("tree-closed");
    $("a[href='admin/get/page.html']").parent().parent("ul").show(100);

</script>
</html>
