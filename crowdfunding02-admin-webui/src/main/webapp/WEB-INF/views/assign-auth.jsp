<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%-- 使用公共的问件头 --%>
<%@include file="../includes/include-css.jsp" %>
<%@include file="../includes/include-js.jsp" %>
<link rel="stylesheet" href="/ztree/zTreeStyle.css">
<script type="text/javascript" src="/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="/js/crowd-auth.js"></script>
<title>分配权限</title>
<body>
<%pageContext.setAttribute("navinfo","- 角色维护" );%>
<%--公共的导航栏--%>
<%@include file="../includes/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">

        <%-- 使用公共的sidebar --%>
        <%@include file="../includes/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="/admin/to/main/page.html">首页</a></li>
                <li><a href="/role/to/page.html">权限分配列表</a></li>
                <li class="active">分配权限</li>
            </ol>
            <input id="hiddenVal" type="hidden" value="${roleId}">
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限分配列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <button id="assignBtn" class="btn btn-success">分配许可</button>
                    <br><br>
                    <ul id="authTreeDemo" class="ztree" style="user-select: none;">
                        <%-- 存放动态生成的树 --%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    $(function () {
        var roleId = $("#hiddenVal").val();
        // 填充分配权限树
        fillAuthTree(roleId);

        // 给分配按钮绑定响应单击事件
        $("#assignBtn").click(function () {
            // 获取选中授权信息
            var authIdArray = [];

            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
            // 获取全部被勾选勾选的
            var checkedNodes = zTreeObj.getCheckedNodes();

            for (var i = 0; i < checkedNodes.length; i++) {

                var checkedNode = checkedNodes[i];

                var authId = checkedNode.id;

                authIdArray.push(authId);
            }
            // 发配请求执行分配

            var requestBody = {
                "authIdArray": authIdArray,
                "roleId": [roleId]
            }
            requestBody = JSON.stringify(requestBody);
            $.ajax({
                "url": "assign/do/role/assign/auth.json",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=utf-8",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        window.location.href = "role/to/page.html";
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！");
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });

        });

    });

    // 点击某个侧边栏  让其处于展开状态
    $("a[href='role/to/page.html']").css("color","red");
    $("a[href='role/to/page.html']").parents(".list-group-item").removeClass("tree-closed");
    $("a[href='role/to/page.html']").parent().parent("ul").show(100);

</script>
</html>
