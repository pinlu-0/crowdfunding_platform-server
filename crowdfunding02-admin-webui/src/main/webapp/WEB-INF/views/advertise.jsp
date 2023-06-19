<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="/WEB-INF/includes/include-css.jsp" %>
    <link rel="stylesheet" href="/css/main.css">
    <title>广告管理</title>
    <% pageContext.setAttribute("navinfo", "- 广告管理");%>
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
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" id="addAdvertiseBtn" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table id="advertiseTable" class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th>广告描述</th>
                                <th>状态</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- 动态生成的数据 --%>
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

<%-- 引入js文件 --%>
<%@include file="/WEB-INF/includes/include-js.jsp" %>


<%-- 添加广告模态框 --%>
<%@include file="modal-adverties-add.jsp" %>

<script type="text/javascript">

    //
    var index;

    /* 查询所有的广告 */
    function getAllAdvertise(pageNum) {
        $.ajax({
            url: "advertise/getAll/advertise.json",
            type: "get",
            data: {pageNum: pageNum},
            beforeSend: function () {
                index = layer.load(0, {shade: false, offset: ['50%', '50%']});
            },
            success: function (data) {
                if (data.list.size == 0) {
                    layer.msg("对不起，没有查到数据");
                    return false;
                }
                // 显示表格数据
                showTableData($("#advertiseTable"), data.list);
                // 显示分页条
                buildPagination(data);
                // 关闭加载进度
                layer.close(index);
            }
        });
    }

    /* 动态生成表格 */
    function showTableData(table, tableData) {
        // 清除上次查询的数据
        table.find("tbody").empty();
        // 遍历
        $.each(tableData, function () {
            // 创建tr
            var tr = $("<tr></tr>")
                .append("<td>" + this.id + "</td>")
                .append("<td>" + this.name + "</td>")
                // 状态：0 未审核 1 审核成功  2 已发布（要发布到首页） 3 已移除（逻辑删除）
                .append("<td>" + this.status + "</td>");

            // 创建操作按钮
            var showImageBtn = $("<button type='button' url='" + this.url + "' class='btn btn-success btn-xs showImg'><i class='glyphicon glyphicon-eye-open'></i></button>");
            var editBtn = $("<button></button>").attr({
                type: "button",
            }).addClass("btn btn-success btn-xs").append("<i class='glyphicon glyphicon-pencil'></i>");
            var deleteBtn = $("<button type='button' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-remove'></i></button>");

            // 创建td
            var td = $("<td></td>")
                .append(showImageBtn)
                .append(" ")
                .append(editBtn)
                .append(" ")
                .append(deleteBtn);
            td.appendTo(tr);
            /* 整个表格构建完成 */
            table.find("tbody").append(tr);
        });
    }

    /* 动态构建分页条 */
    function buildPagination(pageInfo) {
        // 清空分页条
        $("#Pagination").empty();

        console.log(pageInfo);
        var ul = $("<ul></ul>").addClass("pagination");
        // 首页
        ul.append($("<li><a onclick='getAllAdvertise(" + pageInfo.firstPage + ")'>首页</a></li>"));

        // 上一页
        if (pageInfo.hasPreviousPage) {
            ul.append($("<li><a onclick='getAllAdvertise(" + pageInfo.prePage + ")'>上一页</a></li>"));
        } else {
            ul.append($("<li><a>上一页</a></li>").addClass("disabled"));
        }

        // 连续显示页码
        var nums = pageInfo.navigatepageNums;
        $.each(nums, function () {
            // 连续页码
            if (pageInfo.pageNum == this) {
                // 判断当前页并高亮
                ul.append($("<li><a onclick='getAllAdvertise(" + this + ")'>" + this + "</a></li>").addClass("active"));
            } else {
                ul.append($("<li><a onclick='getAllAdvertise(" + this + ")'>" + this + "</a></li>"));
            }
        });

        // 下一页
        if (pageInfo.hasNextPage) {
            ul.append($("<li><a onclick='getAllAdvertise(" + pageInfo.nextPage + ")'>下一页</a></li>"));
        } else {
            ul.append($("<li><a>下一页</a></li>").addClass("disabled"));
        }

        // 末页
        ul.append($("<li><a onclick='getAllAdvertise(" + pageInfo.lastPage + ")'>末页</a></li>"));

        ul.appendTo($("#Pagination"));
    }

    /* 主函数 */
    $(function () {
        /* 页面加载完成就调用查询函数 */
        getAllAdvertise(1);

        /* 添加模态框 */
        $("#addAdvertiseBtn").click(function () {
            // 清空表单
            document.getElementById("uploadForm").reset();
            // 清空图片预览
            $("#addAdvertiseModal").find("#preview").empty();
            // 打开模态框
            $("#addAdvertiseModal").modal("show");
        });


        /* 选择文件按钮 */
        $("#choiceFileBtn").on("click", function () {
            $("#file").click();
        });


        /* 预览图片按钮 */
        $("#previewImg").click(function () {
            $("#preview").empty();
            var file = $("input[name=file]").val();
            if (file.length == 0) {
                layer.msg("请选择目标文件");
                return false;
            }
            var reader = new FileReader();
            //依次读取图片
            reader.readAsDataURL($("#file")[0].files[0]);
            //读取完毕回调
            reader.onload = function (e) {
                var img = $("<img width='200' height='200' >").attr('src', e.target.result);
                //将其追加到页面上
                $("#preview").append(img);
            }
        });


        /* 确认上传按钮事件 */
        $("#sureAddBtn").click(function () {
            // 获取广告描述
            var advDesc = $("#desc").val();
            // 获取广告文件名称
            var fileName = $("input[name=file]").val();
            if (fileName.length == 0 || fileName == "") {
                layer.msg("请选择上传的目标文件");
                return false;
            }
            // 判断文件类型
            var fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (fileType != "jpg" && fileType != "png") {
                layer.msg("请选择后缀为jpg/png的文件");
                return false;
            }

            // 使用FormData对象实现上传
            var formData = new FormData();
            formData.append("advDesc", advDesc);
            formData.append("file", $("#file")[0].files[0]);
            // 发送上传广告的请求
            $.ajax({
                url: "advertise/upload/picture.json",
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.result == "SUCCESS") {
                        layer.msg("上传成功");
                        // 再次查询（相当于刷新）
                        getAllAdvertise(999999);
                    } else {
                        layer.msg("上传失败");
                        return false;
                    }
                }
            });
            // 关闭模态框
            $("#addAdvertiseModal").modal("hide");
        });

        /* 点击小眼睛按钮查看广告图片 */
        $("body").on("click", ".showImg", function () {
            var imgUrl = $(this).attr("url");
            layer.open({
                title: "预览",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['60%', '60%'], //宽高
                maxmin: true, //是否开启最大最小化功能
                anim: 4,
                content: "<img src='https://" + imgUrl + "'/>"
            });
        });


    });


    /* 点击某个侧边栏  让其处于展开状态 */
    $("a[href='advertise/to/getAllAdvertise.html']").css("color", "red");
    $("a[href='advertise/to/getAllAdvertise.html']").parents(".list-group-item").removeClass("tree-closed");
    $("a[href='advertise/to/getAllAdvertise.html']").parent().parent("ul").show(100);
</script>
</body>
</html>

