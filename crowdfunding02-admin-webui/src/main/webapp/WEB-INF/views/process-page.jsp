<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/include-css.jsp" %>
    <link rel="stylesheet" href="/css/main.css">
    <title>流程管理</title>
    <% pageContext.setAttribute("navinfo", "- 流程管理");%>
    <!-- 引入公共头部标签 -->
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

                    <button id="uploadProcessBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-upload"></i>
                        上传流程定义文件
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table id="processTable" class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="auto">#</th>
                                <th>流程名称</th>
                                <th>流程版本</th>
                                <th>任务名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- 表格数据 --%>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td id="Pagination" colspan="4" align="center">
                                    <%-- 显示分页条 --%>
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

<!-- 引入添加流程的模态框 -->
<%@include file="modal-process-add.jsp" %>

<!-- 引入js文件 -->
<%@include file="/WEB-INF/includes/include-js.jsp" %>

<script type="text/javascript">

    //
    var index;

    /* 定义查询所有流程定义信息 */
    function getAllProcess(pageNum) {
        $.ajax({
            url: "process/getAll/process.json",
            type: "post",
            data: {pageNum: pageNum},
            dataType: "json",
            beforeSend: function () {
                // 发送请求显示加载图标
                index = layer.load(0, {shade: false, offset: ['50%', '50%']});
            },
            success: function (data) {
                // 调用生成表格的函数
                generateTable($("#processTable"), data);
                // 显示分页条
                buildPagination();
                // 关闭加载图标
                layer.close(index);
            }
        });
    }

    /* 动态生成table表格 */
    function generateTable(table, tableData) {
        // 清除一下表格
        table.find("tbody").empty();
        $.each(tableData, function () {
            var tr = $("<tr></tr>")
                .append("<td>" + this.id + "</td>")
                .append("<td>" + this.name + "</td>")
                .append("<td>" + this.version + "</td>")
                .append("<td>人工审核</td>");

            // 创建功能按钮
            var previewBtn = $("<button type='button' pid='" + this.id + "' class='btn btn-success btn-xs showImg'><i class='glyphicon glyphicon-eye-open'></i></button>");
            var deleteBtn = $("<button type='button' dpid='" + this.deploymentId + "' class='btn btn-danger btn-xs delProcessBtn'><i class='glyphicon glyphicon-remove'></i></button>");

            // 创建功能td列
            var td = $("<td></td>")
                .append(previewBtn)
                .append(" ")
                .append(deleteBtn);

            // 将功能td列加入tr
            td.appendTo(tr);

            // 将tr加入tbody中
            table.find("tbody").append(tr);
        })
    }

    /* 动态生成分页条 */
    function buildPagination() {
        // 清空分页条
        $("#Pagination").empty();

        // 每页显示的5条数据
        var pageSize = 5;

        // 当前页
        var currentPage = 1;

        // 总记录数
        var totalCount = 0;
        $.ajaxSetup({async: false});
        $.getJSON("process/getJSON", function (data) {
            totalCount = data;
        });

        // 总页数 =  总条数 / pageSize
        var totalPages = Math.ceil(totalCount / pageSize);

        var ul = $("<ul></ul>").addClass("pagination");

        // 首页
        ul.append($("<li><a onclick='getAllProcess(" + 1 + ")'>首页</a></li>"));

        // 上一页
        ul.append($("<li><a onclick='getAllProcess(" + 1 + ")'>上一页</a></li>"));
        // 连续页码
        for (let i = 1; i <= totalPages; i++) {
            // 判断当前页并高亮
            ul.append($("<li><a onclick='getAllProcess(" + i + ")'>" + i + "</a></li>"));
        }

        // 下一页
        ul.append($("<li><a onclick='getAllProcess(" + (currentPage + 1) + ")'>下一页</a></li>"));

        // 末页
        ul.append($("<li><a onclick='getAllProcess(" + totalPages + ")'>末页</a></li>"));

        ul.appendTo($("#Pagination"));
    }

    /* 点击上传文件按钮打开模态框 */
    $("#uploadProcessBtn").click(function () {
        // 每次打开前都清除一次模态框
        $('.modal-body').find('form').trigger('reset');
        // 打开模态框
        $("#addProcessModal").modal("show");
    });

    /* 文件选择按钮点击事件 */
    $("#choiceFileBtn").click(function () {
        $("#file").click();
    });

    /* 确认上传按钮事件 */
    $("#sureAddBtn").click(function () {
        // 判断是否选中上传文件
        var flag = $("#file").val();
        if (flag == "" && flag.length == 0) {
            layer.msg("请选择目标文件");
            return false;
        }

        // 判断文件类型
        var fileType = flag.substring(flag.lastIndexOf(".") + 1).toLowerCase();
        console.log(fileType);
        if (fileType != "xml" && fileType != "bpmn") {
            layer.msg("请选择后缀为 xml/bpmn 的文件");
            return false;
        }
        // 获取表单数据
        var formData = new FormData();
        formData.append("file", $("input[name=file]")[0].files[0]);
        $.ajax({
            url: "process/upload/process.json",
            type: "post",
            data: formData,
            dataType: "json",
            contentType: false,
            processData: false,
            success: function (data) {
                console.log(data);
                if (data == null) {
                    layer.msg("上传失败");
                }
                layer.msg("上传成功");
                // 上传成功后刷新
                getAllProcess(1);
            }
        });
        // 关闭模态框
        $("#addProcessModal").modal("hide");
    });

    /* 预览图片 */
    $("body").on("click", ".showImg", function () {
        // 获取点击的id
        var pid = $(this).attr("pid");
        layer.open({
            title: "预览",
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['60%', '60%'], //宽高
            maxmin: true, //是否开启最大最小化功能
            anim: 4,
            content: "<img src='process/get/process/img.json?pid=" + pid + "'/>"
        });
    });


    /* 删除流程定义文件按钮点击事件 */
    $("body").on("click", ".delProcessBtn", function () {
        var pid = $(this).attr("dpid");
        $.ajax({
            url: "process/delete/process/file.json",
            type: "post",
            data: {processDefinitionId: pid},
            dataType: "json",
            success: function (data) {
                if (data.result == "SUCCESS") {
                    layer.msg("删除成功");
                    getAllProcess(1);
                } else {
                    layer.msg("删除失败，错误代码：0");
                    return false;
                }
            }
        });
    });


    /* 主函数，页面一加载就执行 */
    $(function () {
        // 页面加载就查询所有数据并装入动态生成的表格
        getAllProcess(1);

    });

    /* 点击某个侧边栏  让其处于展开状态 */
    $("a[href='process/to/process/page.html']").css("color", "red");
    $("a[href='process/to/process/page.html']").parents(".list-group-item").removeClass("tree-closed");
    $("a[href='process/to/process/page.html']").parent().parent("ul").show(100);


</script>
</html>

