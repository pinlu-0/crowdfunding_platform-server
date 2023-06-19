<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/include-css.jsp" %>
    <link rel="stylesheet" href="/css/main.css">
    <title>账户类型资质</title>
    <% pageContext.setAttribute("navinfo", "- 分类管理");%>
    <%@include file="/WEB-INF/includes/include-nav.jsp" %>
</head>

<body>

<%-- 引入公共头部 --%>
<%@include file="/WEB-INF/includes/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%-- 引入公共的sidebar --%>
        <%@include file="/WEB-INF/includes/include-sidebar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据矩阵</h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table  table-bordered" style="text-align: center">
                            <span style="color: red">注：此表以列为主</span>
                            <thead valign="middle">
                            <tr>
                                <th>资质名称</th>
                                <c:forEach items="${accountTypeList}" var="type">
                                    <th>${type.accountType}</th>
                                </c:forEach>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${certPageInfo.list}" var="cert" varStatus="certstatus">
                                <tr>
                                    <td>${cert.name}</td>
                                    <c:forEach items="${accountTypeList}" var="type" varStatus="typestatus">
                                        <td>
                                                <%-- 坐标 (${certstatus.index}, ${typestatus.index}); --%>
                                                <%-- 取出二维数组中的坐标的值，若果是true就选中，否则不选 --%>
                                            <input class="acctCertCheckBtn" type="checkbox" cid="${cert.id}"
                                                   acctid="${type.id}" ${relations[certstatus.index][typestatus.index] == true?"checked":""}>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <%-- 首页 --%>
                                        <li><a href="type/getalltype.html?pageNum=1">首页</a></li>
                                        <%-- 判断是否有上一页 --%>
                                        <c:if test="${certPageInfo.hasPreviousPage}">
                                            <li>
                                                <a href="type/getalltype.html?pageNum=${certPageInfo.prePage}">上一页</a>
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
                                                    <a href="type/getalltype.html?pageNum=${currentPage}">${currentPage}</a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                        <%-- 判断是否有下一页 --%>
                                        <c:if test="${certPageInfo.hasNextPage}">
                                            <li>
                                                <a href="type/getalltype.html?pageNum=${certPageInfo.nextPage}">下一页</a>
                                            </li>
                                        </c:if>
                                        <%-- 末页 --%>
                                        <li><a href="type/getalltype.html?pageNum=${certPageInfo.lastPage}">末页</a>
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
</body>
<%-- 引入js文件 --%>
<%@include file="/WEB-INF/includes/include-js.jsp" %>

<script type="text/javascript">

    var index;

    $(function () {

        $("table").on("click", ".acctCertCheckBtn", function () {
            console.log($(this).attr("cid"),$(this).attr("acctid"));
            var cid = $(this).attr("cid");
            var acctid = $(this).attr("acctid");
            $.ajax({
                "url":"type/checked/cert.json",
                "type":"post",
                "data":{"certId":cid,"acctId":acctid},
                "dataType":"json",
                beforeSend:function () {
                    index=layer.load(0,{shade:false,offset:['50%',['50%']]});
                },
                "success":function (response) {
                    console.log(response);
                    layer.close(index);
                    if(response.result=="SUCCESS"){
                        window.location.href="type/getalltype.html";
                    }
                }
            });
        })
    });
    /* 点击某个侧边栏  让其处于展开状态 */
    $("a[href='type/getalltype.html']").css("color", "red");
    $("a[href='type/getalltype.html']").parents(".list-group-item").removeClass("tree-closed");
    $("a[href='type/getalltype.html']").parent().parent("ul").show(100);
</script>
</html>
