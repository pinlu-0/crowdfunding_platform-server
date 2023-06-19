<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>项目审核</title>
    <%-- 引入公共的 CSS 文件 --%>
    <%@include file="../includes/include-css.jsp" %>
    <%-- 引入公共的 JS 文件 --%>
    <%@include file="../includes/include-js.jsp" %>
    <%-- 分页条样式 --%>
    <link rel="stylesheet" href="/css/pagination.css">
    <%-- 分页插件 --%>
    <script type="text/javascript" src="/jquery/jquery.pagination.js" charset="utf-8"></script>

</head>
<body>

<%pageContext.setAttribute("navinfo","- 项目审核" );%>
<%-- 公共的顶部导航栏 --%>
<%@include file="../includes/include-nav.jsp" %>

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
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th>项目名称</th>
                                <th>发起人</th>
                                <th>目标金额（元）</th>
                                <th>众筹周期(天)</th>
                                <th>创建时间</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>XXXXXXXXXXXX项目</td>
                                <td>XXXXXXXXXXXX公司</td>
                                <td>1000000.00</td>
                                <td>30</td>
                                <td>2017-06-01 19:00:00</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i
                                            class="glyphicon glyphicon-eye-open"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>XXXXXXXXXXXX项目</td>
                                <td>XXXXXXXXXXXX公司</td>
                                <td>1000000.00</td>
                                <td>30</td>
                                <td>2017-06-01 19:00:00</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i
                                            class="glyphicon glyphicon-eye-open"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>XXXXXXXXXXXX项目</td>
                                <td>XXXXXXXXXXXX公司</td>
                                <td>1000000.00</td>
                                <td>30</td>
                                <td>2017-06-01 19:00:00</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i
                                            class="glyphicon glyphicon-eye-open"></i></button>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="7" align="center">
                                    <ul class="pagination">
                                        <li class="disabled"><a href="#">上一页</a></li>
                                        <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">5</a></li>
                                        <li><a href="#">下一页</a></li>
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
<script type="text/javascript">
    /* JS 代码编辑区 */



    // 点击某个侧边栏  让其处于展开状态
    $("a[href='audi/auth_project.html']").css("color", "red");
    $("a[href='audi/auth_project.html']").parents(".list-group-item").removeClass("tree-closed");
    $("a[href='audi/auth_project.html']").parent().parent("ul").show(100);
</script>
</html>
