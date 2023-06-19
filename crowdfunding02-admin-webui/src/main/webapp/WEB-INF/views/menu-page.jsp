<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<title>菜单维护</title>
<%-- 使用公共的问价头 --%>
<%@include file="../includes/include-css.jsp" %>
<link rel="stylesheet" href="../../ztree/zTreeStyle.css">
<%@include file="../includes/include-js.jsp" %>
<script type="text/javascript" src="../../ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="../../js/crowd-menu.js"></script>

<body>
<%pageContext.setAttribute("navinfo", "- 菜单维护");%>
<%--公共的导航栏--%>
<%@include file="../includes/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%-- 使用公共的sidebar --%>
        <%@include file="../includes/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <%-- 这个ul是zTree动态生成的结点所依附的静态结点--%>
                    <ul id="treeDemo" class="ztree" style="user-select: none;">

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- 引入模态框外部文件 --%>
<%@include file="modal-menu-add.jsp" %>
<%@include file="modal-menu-edit.jsp" %>
<%@include file="modal-menu-confirm.jsp" %>

</body>
<script type="text/javascript">
    // 发送请求
    $(function () {

        /*查询树*/
        generateTree();
        /**
         * 给添加子节点的按钮绑定单击响应事件
         */
        $("#treeDemo").on("click", ".addBtn", function () {
            // 将当前节点的id作为新增节点的pid 保存到全局变量中
            window.pid = this.id;
            // 打开模态框
            $("#menuAddModal").modal("show");
            // 禁止超链接的默认行为
            return false;
        });


        /**
         * 给添加新节点的模态框中的确认按钮绑定单击响应事件
         */
        $("#menuSaveBtn").click(function () {
            // 获取表单数据
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $("#menuAddModal [name=icon]:checked").val();

            $.ajax({
                "url": "menu/save.json",
                "type": "POST",
                "data": {
                    "pid": window.pid,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 刷新页面
                        generateTree();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.errorMessage);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 关闭模态框
            $("#menuAddModal").modal("hide");

            // 清空表单
            $("#menuResetBtn").click();
        });

        /**
         * 给编辑结点的按钮绑定单击响应事件
         */
        $("#treeDemo").on("click", ".editBtn", function () {
            // 将当前节点的id作为新增节点的pid 保存到全局变量中
            window.id = this.id;
            // 打开模态框
            $("#menuEditModal").modal("show");
            // 获取zTree对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            // 根据id属性查询结点对象
            // 用来搜索结点的属性名
            var key = "id";
            // 用来搜索结点的属性值
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key, value);
            // 回显表单数据
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            // 被选中的value可以组成一个数组，然后在用这个数组设置回显radio，就能把对应的值选中
            $("#menuEditModal [name=icon]").val([currentNode.icon]);
            // 禁止超链接的默认行为
            return false;
        });

        /**
         * 给模态框中的更新按钮绑定单击响应事件
         */
        $("#menuEditBtn").click(function () {
            // 拿到模态框中回显的值
            var nodeName = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            var icon = $("#menuEditModal [name=icon]:checked").val();
            // 发送ajax请求
            $.ajax({
                "url": "menu/edit.json",
                "type": "post",
                "data": {
                    "id": window.id,
                    "name": nodeName,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 刷新树
                        generateTree();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.errorMessage);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 关闭模态框
            $("#menuEditModal").modal("hide");
        });

        /**
         * 删除节点
         */
        $("#treeDemo").on("click", ".removeBtn", function () {
            // 拿到要删除节点的id
            window.id = this.id;
            // 弹出确认删除模态框
            $("#menuConfirmModal").modal("show");
            // 获取zTree对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam("id", value);
            $("#removeNodeSpan").html("【<i class='" + currentNode.icon + "'></i>" + currentNode.name + "】");
            return false;
        });
        //确认删除
        $("#confirmBtn").click(function () {
            $.ajax({
                "url": "menu/remove.json",
                "type": "post",
                "data": {
                    "id": window.id
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    console.log(result);
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 刷新页面
                        generateTree();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！");
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            // 关闭模态框
            $("#menuConfirmModal").modal("hide");
        });

    });

    // 点击某个侧边栏  让其处于展开状态
    $("a[href='menu/to/page.html']").css("color", "red");
    $("a[href='menu/to/page.html']").parents(".list-group-item").removeClass("tree-closed");
    $("a[href='menu/to/page.html']").parent().parent("ul").show(100);

</script>
</html>
