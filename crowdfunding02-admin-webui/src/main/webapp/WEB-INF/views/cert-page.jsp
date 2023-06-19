<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/include-css.jsp" %>
    <link rel="stylesheet" href="/css/main.css">
    <title>控制面板</title>
    <% pageContext.setAttribute("navinfo", "- 资质维护");%>
    <%@include file="/WEB-INF/includes/include-nav.jsp" %>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <%-- sidebar --%>
        <%@include file="/WEB-INF/includes/include-sidebar.jsp" %>

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
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="removeCertBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" id="addCertBtn" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="all_checked" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.certPageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉！没有查到你需要的数据！</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.certPageInfo.list}">
                                <c:forEach items="${requestScope.certPageInfo.list}" var="cert">
                                    <tr>
                                        <td>${cert.id}</td>
                                        <td><input class="single_checked" type="checkbox"></td>
                                        <td>${cert.name}</td>
                                        <td>
                                            <button type="button" class="btn btn-primary btn-xs certEditBtn"><i
                                                    class=" glyphicon glyphicon-pencil"></i></button>
                                            <button type="button" class="btn btn-danger btn-xs"><i
                                                    class=" glyphicon glyphicon-remove certRemoveBtn"></i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <%-- 首页 --%>
                                        <li><a href="cert/getallcert.html?pageNumber=1">首页</a></li>
                                        <%-- 判断是否有上一页 --%>
                                        <c:if test="${certPageInfo.hasPreviousPage}">
                                            <li>
                                                <a href="cert/getallcert.html?pageNumber=${certPageInfo.prePage}">上一页</a>
                                            </li>
                                        </c:if>
                                        <%-- 遍历连续显示的页码 --%>
                                        <c:forEach items="${certPageInfo.navigatepageNums}" var="currentPage">
                                            <%-- 如果所在页等于当前页 --%>
                                            <c:if test="${currentPage == certPageInfo.pageNum}">
                                                <%-- 高亮当前页 --%>
                                                <li class="active"><a>${currentPage}<span
                                                        class="sr-only">(current)</span></a></li>
                                            </c:if>
                                            <c:if test="${currentPage != certPageInfo.pageNum}">
                                                <li>
                                                    <a href="cert/getallcert.html?pageNumber=${currentPage}">${currentPage}</a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                        <%-- 判断是否有下一页 --%>
                                        <c:if test="${certPageInfo.hasNextPage}">
                                            <li>
                                                <a href="cert/getallcert.html?pageNumber=${certPageInfo.nextPage}">下一页</a>
                                            </li>
                                        </c:if>
                                        <%-- 末页 --%>
                                        <li><a href="cert/getallcert.html?pageNumber=${certPageInfo.lastPage}">末页</a>
                                        </li>
                                    </ul>
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

<%-- 添加资质模态框 --%>
<jsp:include page="modal-cert-add.jsp"></jsp:include>
<%-- 修改资质模态框 --%>
<jsp:include page="modal-cert-edit.jsp"></jsp:include>

<%@include file="/WEB-INF/includes/include-js.jsp" %>
<script type="text/javascript" src="../../jquery/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function () {
        /* 新增按钮点击事件 */
        $("#addCertBtn").click(function () {
            // 打开添加模态框
            $("#certAddModal").modal("show");
        });

        /* 保存事件 */
        $("#certSaveBtn").click(function () {
            var certName = $("#certName").val();
            $.ajax({
                "url": "cert/savecert.json",
                "type": "POST",
                "data": {name: certName},
                "dataType": "json",
                "success": function (response) {
                    if (response.result == "SUCCESS") {
                        $("#certAddModal").modal("hide");
                        layer.msg("添加成功");
                    }
                    // 刷新页面
                    location.href = "cert/getallcert.html";
                }
            });
        });


        /* 修改按钮事件 */
        $("td").on("click", ".certEditBtn", function () {
            // 选中的资质id
            var certId = $(this).parents().children("td:eq(0)").text();
            // 选中的资质name
            var certName = $(this).parents().children("td:eq(2)").text();
            // 回显值
            $("#certEditModal [name=id] ").val(certId);
            $("#certEditModal [name=name] ").val(certName);
            // 打开修改模态框
            $("#certEditModal").modal("show");
        });

        /* 修改按钮事件 */
        $("#certEditBtn").click(function () {
            var data = {
                "id": $("#certId").val(),
                "name": $("#certEditModal [name=name] ").val()
            }
            $.ajax({
                "url": "cert/editcert.json",
                "type": "post",
                "data": data,
                "dataType": "json",
                "success": function (response) {
                    console.log(response);
                    if (response.result == "SUCCESS") {
                        layer.msg("修改成功");
                    }
                    // 关闭修改模态框
                    $("#certEditModal").modal("hide");
                    location.href = "cert/getallcert.html";
                }
            });
        });

        /**
         * 删除资质信息
         * */

        /* 全选全不选 */
        function checkAllEvent(all_checked, single_checked) {
            // 勾选全选框
            all_checked.click(function () {
                // 给全选框按钮赋选中属性
                var checkedAll = $(this).prop("checked");
                // 让所有的单选框选中
                single_checked.prop("checked", checkedAll);
            });
            // 勾选单选
            single_checked.click(function () {
                var flag = single_checked.filter(":checked").length == single_checked.length;
                all_checked.prop("checked", flag);
            });
        }

        // 调用函数
        checkAllEvent($("#all_checked"), $(".single_checked"));

        /* 批量删除 */
        $("#removeCertBtn").click(function () {
            // 获取选中的单选框的个数
            var count = $(".single_checked:checked").length;
            if (count == 0) {
                layer.msg("请选中要删除的数据");
                return false;
            }
            // 获取所有要删除数据的id
            var ids = "";
            $(".single_checked:checked").each(function () {
                ids += $(this).parent().prev().text() + ",";
            });
            ids = ids.substring(0, ids.length - 1);
            if (confirm("确认删除【" + ids + "】这些资质么？")) {
                // 发送请求
                $.ajax({
                    "url": "cert/remove.json",
                    "type": "post",
                    "data": {id: ids},
                    "dataType": "json",
                    "success": function (response) {
                        console.log(response);
                        if (response.result == "SUCCESS") {
                            layer.msg("删除成功");
                        }
                        location.href = "cert/getallcert.html";
                    }
                });
            }
        });

        /* 单个删除 */
        $(".certRemoveBtn").click(function () {
            var id = $(this).parents().children("td:eq(0)").text();
            $.ajax({
                "url": "cert/remove.json",
                "type": "post",
                "data": {id: id},
                "dataType": "json",
                "success": function (response) {
                    if (confirm("确认删除【" + id + "】资质信息么？")) {
                        if (response.result == "SUCCESS") {
                            layer.msg("删除成功");
                            location.href = "cert/getallcert.html";
                        }
                    }

                }
            });
        });

    });

    /* 点击某个侧边栏  让其处于展开状态 */
    $("a[href='cert/getallcert.html']").css("color", "red");
    $("a[href='cert/getallcert.html']").parents(".list-group-item").removeClass("tree-closed");
    $("a[href='cert/getallcert.html']").parent().parent("ul").show(100);
</script>
</body>
</html>

