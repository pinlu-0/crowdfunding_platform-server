<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>实名认证</title>
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
<%pageContext.setAttribute("navinfo", "- 实名认证");%>
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
                                <th>流程编号</th>
                                <th>流程名称</th>
                                <th>身份证号</th>
                                <th>申请用户</th>
                                <th>任务名称</th>
                                <th>真实姓名</th>
                                <th>资质预览</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pageInfo}" var="infomap" >
                            <tr>
                                <td>${infomap.taskId}</td>
                                <td>实名认证审批流程</td>
                                <td>${infomap.member.cardnum}</td>
                                <td>${infomap.member.loginacct}</td>
                                <td>${infomap.taskName}</td>
                                <td>${infomap.member.realname}</td>
                                <td>
                                    <button class="showImg" imgUrl="<c:forEach items="${infomap.certs}" var="cert">${cert.url},</c:forEach>">点击预览
                                    </button>
                                </td>
                                <td>
                                    <button type="button" memberId="${infomap.member.id}" taskId="${infomap.taskId}" class="btn btn-success btn-xs authBtn"><i
                                            class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i
                                            class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                           <!-- <tr>
                                <td colspan="6" align="center">
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
                            </tr> -->
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



    $("body").on("click",".showImg",function () {
        var imgUrls = $(this).attr("imgUrl");
        var b = imgUrls.substring(0,imgUrls.length-1);
        var a = b.split(",");
        $.each(a,function (i,val) {
            layer.open({
                type: 1,
                title:"资质预览",
                offset: 'auto',
                area: ['800px', '500px'], //宽高
                content:'<img src=https://'+val+ ' />'
            });
        });
    });

    $("body").on("click",".authBtn",function () {

        if(confirm("确认审核么？")){
            // 修改申请用户的申请状态
            var memberId = $(this).attr("memberId");
            var taskId = $(this).attr("taskId");
            // 发送审核请求
            $.ajax({
                url:"complete/auth/real/name.json",
                type:"post",
                data:{memberId:memberId,taskId:taskId},
                dataType:"json",
                success:function (data) {
                    console.log(data);
                }
            });
        }
    });






    // 点击某个侧边栏  让其处于展开状态
    $("a[href='auth/real/name/list.html']").css("color", "red");
    $("a[href='auth/real/name/list.html']").parents(".list-group-item").removeClass("tree-closed");
    $("a[href='auth/real/name/list.html']").parent().parent("ul").show(100);
</script>
</html>
