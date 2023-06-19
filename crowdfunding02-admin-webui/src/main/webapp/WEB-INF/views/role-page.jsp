<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<title>角色维护</title>
<%-- 抽取的公共文件 --%>
<%@include file="../includes/include-css.jsp" %>
<%@include file="../includes/include-js.jsp" %>
<body>
<%pageContext.setAttribute("navinfo", "- 角色维护");%>
<%-- 公共的顶部导航栏 --%>
<%@include file="../includes/include-nav.jsp" %>
<link rel="stylesheet" href="/css/pagination.css">
<link rel="stylesheet" href="/ztree/zTreeStyle.css">
<script type="text/javascript" src="/jquery/jquery.pagination.js" charset="utf-8"></script>
<script type="text/javascript" src="/js/crowd-role.js"></script>
<script type="text/javascript" src="/ztree/jquery.ztree.all-3.5.min.js"></script>


<div class="container-fluid">
    <div class="row">
        <%-- 使用公共的侧边导航栏sidebar --%>
        <%@include file="../includes/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="checkedBox" type="checkbox"></th>
                                <th>角色名称</th>
                                <th width="130">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            <%-- 使用js动态填充表格内容 --%>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="4" align="center">
                                    <div id="Pagination" class="pagination">
                                        <!-- 这里显示分页 -->
                                    </div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- 引入添加角色模态框 --%>
<%@include file="/WEB-INF/views/modal-role-add.jsp" %>

<%-- 引入编辑角色模态框 --%>
<%@include file="modal-role-edit.jsp" %>

<%-- 引入确认模态框 --%>
<%@include file="modal-role-confirm.jsp" %>

<%-- 引入分配权限模态框 --%>
<%@include file="modal-role-auth.jsp" %>

</body>
<script type="text/javascript">
    $(function () {
        /**
         * 1.为分页操作准备初始化数据
         * **/
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        /**
         * 2.调用执行分页的函数，显示分页效果
         * **/
        generatePage();

        /**
         * 3.给查询按钮绑定单击事件
         * **/
        $("#searchBtn").click(function () {
            // 获取关键词数据赋给对应的全局变量
            window.keyword = $("#keywordInput").val();
            // 使用分页函数刷新页面
            generatePage();
        });

        /**
         * 4.点击新增按钮打开模态框
         * */

        $("#showAddModalBtn").click(function () {
            // 打开之前清空一下模态框
            $("#addModal [name=roleName]").val("");
            // 打开模态框
            $('#addModal').modal('show');
        });

        /**
         * 5.给保存按钮绑定点击事件
         * */
        $("#saveRoleBtn").click(function () {
            // 获取文本框中输的内容,使用trim去除空格
            var roleName = $.trim($("#addModal [name=roleName]").val());
            if (roleName == null || roleName.length == 0 || roleName == "") {
                layer.msg("角色名称不能为空值！");
                return;
            }
            // 在保存前查询数据库是否已经存在该角色
            $.ajax({
                "url": "role/find/exist/role",
                "type": "post",
                "data": {name: roleName},
                "dataType": "json",
                "success": function (response) {
                    console.log(response);
                    // 如果返回结果为1说明要添加的角色已经存在
                    if (response.data == 1) {
                        layer.msg("该角色已存在");
                        return false;
                    }
                    // 为0代表角色不存在，则执行添加操作
                    if (response.data == 0) {
                        // 发送保存请求
                        $.ajax({
                            "url": "role/save.json",
                            "type": "post",
                            "data": {
                                "name": roleName
                            },
                            "dataType": "json",
                            "success": function (response) {
                                var result = response.result;
                                if (result == "SUCCESS") {
                                    layer.msg("操作成功！");
                                    // 重新加载分页
                                    window.pageNum = 99999;
                                    generatePage();
                                }
                                if (result == "FAILED") {
                                    layer.msg("操作失败！" + " " + response.message);
                                }
                            },
                            "error": function (response) {
                                layer.msg(response.status + " - " + response.statusText);
                            }
                        });

                    }
                },
                "error": function (response) {
                    layer.msg(response.statusText + " " + response.statusCode());
                }
            });

            // 关闭模态框
            $('#addModal').modal('hide');

        });


        // 传统的绑定的方式只在第一页有效，一但翻其他页就能使用了
        // $(".editBtn").click(function () {
        //
        // });

        // 使用JQuery对象的on()函数可以解决上边的问题
        // 首先找到所有“动态生成”的元素所附着的“静态”元素
        // 第一个参数：事件类型    第二个参数：真正要绑定事件的元素的选择器  第三个参数：响应函数
        /**
         * 6.给每行数据的编辑按钮绑定单击响应事件
         */
        $("#rolePageBody").on("click", ".editBtn", function () {
            // 打开模态框
            $("#editModal").modal("show");
            // 获取表格中当前行中的名称
            var roleName = $(this).parent().prev().text();

            // 获取当前角色的id   editBtn = "<button id='"+roleId+"'
            window.roleId = this.id;

            // 在模态框中回显值
            $("#editModal [name=roleName]").val(roleName);
        });

        /**
         * 给更新模态框中的更新按钮绑定响应单击事件
         */
        $("#editRoleBtn").click(function () {
            var roleName = $("#editModal [name=roleName]").val();
            $.ajax({
                "url": "role/edit.json",
                "type": "post",
                "data": {
                    "id": window.roleId,
                    "name": roleName
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 刷新页面
                        generatePage();
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
            $("#editModal").modal("hide");
        });

        /**
         * 点击模态框中的确认按钮 发送删除请求
         * */
        $("#removeRoleBtn").click(function () {
            // 从全局变量范围内的roleIdArray数组中获取要删除的角色的id
            var requestBody = JSON.stringify(window.roleIdArray);
            $.ajax({
                "url": "role/remove/by/role/id/array.json",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=utf-8",
                "success": function (response) {
                    var result = response.result;
                    console.log(result);
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        generatePage();
                        if ($("#checkedBox").is(":checked")) {
                            $("#checkedBox").prop("checked", false);
                        }
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！");
                    }
                },
                "error": function (response) {
                    layer(response.status + " " + response.statusText);
                }
            });
            // 关闭模态框
            $("#confirmModal").modal("hide");
        });

        // 给单条删除按钮绑定单击响应按钮
        $("#rolePageBody").on("click", ".removeBtn", function () {
            // 获取修改当前行roleName
            var roleName = $(this).parent().prev().text();
            // 创建role对象
            var roleArray = [{
                "roleId": this.id,
                "roleName": roleName
            }];
            showConfirmModal(roleArray);
        });

        /**
         *  批量删除
         */
        // 给总的checkBox绑定单击响应事件
        $("#checkedBox").click(function () {
            // 获取多选框的自身的状况
            var currentStatus = this.checked;
            // 用当前多选框的状态设置其他多选框为选中状态
            $(".itemBox").prop("checked", currentStatus);
        });

        // 全选、全不选的反向操作
        $("#rolePageBody").on("click", ".itemBox", function () {
            // 获取当前选中的.itemBox的数量
            var checkedBoxCount = $(".itemBox:checked").length;
            // 获取总的.itemBox数量
            var totalBoxCount = $(".itemBox").length;
            // 用当前多选框的状态设置其他多选框为选中状态
            $("#checkedBox").prop("checked", checkedBoxCount == totalBoxCount);

        });

        /**
         * 给批量删除按钮绑定单击响应按钮
         * */
        $("#batchRemoveBtn").click(function () {
            // 声明一个数组用来存放要删除的角色
            var roleArray = [];
            // 遍历选中的多选框
            $(".itemBox:checked").each(function () {
                // 使用this引用当前遍历得到的多选框
                var roleId = this.id;
                // 通过DOM操作获取角色名称
                var roleName = $(this).parent().next().text();
                roleArray.push({
                    "roleId": roleId,
                    "roleName": roleName
                });
            });
            // 检查roleArray长度
            if (roleArray.length == 0) {
                layer.msg("请至少选择一条数据删除！");
                return;
            }
            showConfirmModal(roleArray);
        });

        /**
         * 给分配权限按钮绑定单级响应事件
         */
        $("#rolePageBody").on("click", ".checkBtn", function () {
            window.location.href = "to/assign.html/" + this.id;
        });

        /**
         * 分配菜单权限按钮点击事件
         */
        $("#rolePageBody").on("click", ".authBtn", function () {
            // 1. 获取所点击用户的id

            // 2. 封装树
            initAuthTree(this.id);
            // 3. 设置模态框
            var options = {
                backdrop: 'static',// 点击背景不关闭模态框
                show: true
            };
            // 4.打开模态框
            $('#RoleAuthModal').modal(options);
            // 打开模态框是 将角色id传到模态框中
            $("#authRoleMenu").attr("rid", this.id);
        });

        /**
         * 显示图标
         * */
        function showIcon(treeId, treeNode) {
            // 移除默认图标
            $("#" + treeNode.tId + "_ico").removeClass();
            // 添加一个span来显示自定义的图标
            $("#" + treeNode.tId + "_ico").before("<span class='" + treeNode.icon + "'></span>");

        }

        /**
         * 封装树的函数
         * */
        function initAuthTree(rid) {
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "pid"
                    },
                    key: {
                        url: "xUrl"
                    }
                },
                view: {
                    addDiyDom: showIcon
                },
                check: {
                    enable: true
                }
            };

            /* 发送查询出所有的权限请求 */
            $.getJSON("role/get/auths.json", function (nodes) {
                // console.log(nodes);
                $.each(nodes, function () {
                    // 如果pid为0说明是父菜单，就将其展开
                    if (this.pid == 0) {
                        this.open = true;
                    }
                });
                // 初始化树形结构
                ztreeObject = $.fn.zTree.init($("#authTreeDemo"), setting, nodes);
                // 将当前用户拥有的权限选中  参数：所点击用户的id
                checkCurrentMenu(rid);
            });
        }

        /**
         * 传入角色id，将当前角色拥有的菜单权限勾选上
         * */
        function checkCurrentMenu(roleId) {
            /* 发送请求查询当前角色拥有的权限 */
            $.getJSON("role/get/role/" + roleId, function (response) {
                // 选中该角色拥有的所有菜单权限
                $.each(response, function () {
                    var node = ztreeObject.getNodeByParam("id", this.id, null);
                    ztreeObject.checkNode(node, true, false);
                });
            });
        }

        /**
         * 授权按钮点击事件
         * */
        $("#authRoleMenu").click(function () {
            /* 1. 获取当前选中的权限 */
            var nodes = ztreeObject.getCheckedNodes(true);
            // 菜单id
            var menuIds = "";
            // 角色id
            var rid = $(this).attr("rid");
            /* 2. 讲这些权限的id和角色id发给程序*/
            $.each(nodes, function () {
                menuIds += this.id + ",";
            });
            /* 发送请求 */
            $.ajax({
                "url": "role/assign/role/menu/assign",
                "type": "post",
                "data": {"mids": menuIds, "rid": rid},
                "success": function (response) {
                    console.log(response);
                    if(response.result=="SUCCESS"){
                        layer.msg("分配成功");
                        $('#RoleAuthModal').modal("hide");
                    }else{
                        layer.msg("对不起，你没有操作该项的权限！");
                        $('#RoleAuthModal').modal("hide");
                        return false;
                    }
                },
                "error": function (response) {

                }
            });
        });

        /**
         * 点击某个侧边栏  让其处于展开状态
         */
        $("a[href='role/to/page.html']").css("color", "red");
        $("a[href='role/to/page.html']").parents(".list-group-item").removeClass("tree-closed");
        $("a[href='role/to/page.html']").parent().parent("ul").show(100);

    });
</script>
</html>
