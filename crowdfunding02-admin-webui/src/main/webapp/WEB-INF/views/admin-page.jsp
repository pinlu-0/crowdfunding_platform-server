<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<title>用户维护</title>
<%-- 使用公共的问件头 --%>
<%@include file="../includes/include-css.jsp" %>
<link rel="stylesheet" href="../../css/pagination.css">
<style>
    .tree li {
        list-style-type: none;
        cursor: pointer;
    }

    table tbody tr:nth-child(odd) {
        background: #F4F4F4;
    }

    table tbody td:nth-child(even) {
        color: #C00;
    }
</style>
<%-- 引入公共的JS文件 --%>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page.html" method="post" class="form-inline" role="form"
                          style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件" value="${searchKeyword}">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" id="batchRemoveBtn" class="btn btn-danger"
                            style="float:right;margin-left:10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="checkAllBtn" type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉！没有查到你需要的数据！</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input class="checkSingleBtn" ids="${admin.id}" type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a href="assign/to/assign/role/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}"
                                               class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></a>
                                            <a href="admin/to/edit/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}"
                                               class="btn btn-primary btn-xs"><i
                                                    class=" glyphicon glyphicon-pencil"></i></a>
                                            <a href="admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html"
                                               class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
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

</body>
<script type="text/javascript" src="../../jquery/jquery.pagination.js"></script>
<script type="text/javascript">
    // 用户点击"上一页、1、2、3、下一页"这样的页码时，调用这个函数
    // page_index：Pagination内部传的从0开始的页码
    function pageSelectCallback(page_index, JQuery) {
        // 根据page_index计算pageNum
        var pageNum = page_index + 1;
        // 跳转页面
        window.location.href = "/admin/get/page.html?pageNum=" + pageNum + "&keyword=${param.keyword}";
        // 取消超链接默认行为
        return false;
    }

    // 声明一个JSON对象设置Pagination的属性
    var paginationProperties = {
        num_edge_entries: 3, // 边缘页数
        num_display_entries: 5, // 主体页数
        callback: pageSelectCallback, // 指定用户点击"翻页"的按钮时跳转页面的回调函数
        items_per_page: ${requestScope.pageInfo.pageSize}, // 每页显示多少条数据
        current_page:${requestScope.pageInfo.pageNum-1}, // Pagination内部属性"current_page"默认是0，表示第1页,PageInfo内部属性"pageNum"默认值为1，所以减1
        prev_text: "上一页", // 上一页按钮上显示的文本
        next_text: "下一页" // 下一页按钮上显示的文本
    };

    // 获取总记录数
    var totalRecord = ${requestScope.pageInfo.total};

    // 生成页码导航条的函数
    function intiPagination() {
        // 生成页码导航条
        $("#Pagination").pagination(totalRecord, paginationProperties);
    }

    $(function () {
        // 调用分页导航条初始化函数
        intiPagination();
    });

    /**
     * 批量删除
     */

    // 选中要删除的数据
    // 参数：全选按钮对象
    function checkAllEvent(check_all_btn, check_single_btn) {
        check_all_btn.click(function () {
            // 勾选全选框
            var checkedAll = $(this).prop("checked");
            // 全选框选中后，选中当前页的全部单选框
            check_single_btn.prop("checked", checkedAll);
        });

        check_single_btn.click(function () {
            // 选中的单选框等于所有的单选框时，让全选框选中
            var flag = check_single_btn.filter(":checked").length == check_single_btn.length;
            check_all_btn.prop("checked", flag);
        });
    }

    checkAllEvent($("#checkAllBtn"), $(".checkSingleBtn"));

    /**
     * 删除按时点击事件
     */
    $("#batchRemoveBtn").click(function () {
        // 获取选中的复选框
        var flag = $(".checkSingleBtn:checked");
        // 提示选中删除数据
        if (flag.length == 0) {
            layer.msg("未选中要删除的数据");
            return false;
        }
        // 遍历选中的复选框中的自定义的ids属性的值
        var ids = "";
        flag.each(function () {
            // 最后会多一个，
            ids += $(this).attr("ids") + ",";
        });
        // 提出最后多的那个，
        ids = ids.substring(0, ids.length - 1);

        // 发送删除请求
        $.ajax({
            "url": "admin/batchDel.json",
            "type": "POST",
            "data": {id: ids},
            "dataType": "json",
            "success": function (response) {
                console.log(response);
                var result = response.result;
                if (confirm("确认删除" + ids + "用户么？")) {
                    if (result == "SUCCESS") {
                        layer.msg("删除成功");
                        location.href = "admin/get/page.html";
                    }
                    if (result == "FAILED") {
                        layer.msg("删除失败");
                        return false;
                    }
                }
            },
            "error": function (response) {
                layer.msg(response.statusText + " " + response.statusCode());
            }
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
