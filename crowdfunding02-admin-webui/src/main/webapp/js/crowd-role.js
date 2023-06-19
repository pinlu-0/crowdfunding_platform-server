/*自定义js文件*/

// 声明专门显示确认模态框的函数
function showConfirmModal(roleArray) {

    // 清除模态框上次打开选中的内容
    $("#roleNameDiv").empty();

    // 打开确认模态框
    $("#confirmModal").modal("show");

    // 声明roleId全局变量数组
    window.roleIdArray = [];

    // 遍历roleArray数组
    for (var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];
        var roleName = role.roleName + " ";
        $("#roleNameDiv").append(roleName);
        var roleId = role.roleId;

        // 调用数组的push方法存入新的元素
        window.roleIdArray.push(roleId);
    }
}

// 执行分页，生成页面效果。任何时候调用这个函数都会重新加载页面
function generatePage() {

    // 1.获取分页数据
    var pageInfo = getPageInfoRemote();

    // 2.填充表格
    fillTableBody(pageInfo);

}

// 访问数据库端获取PageInfo中的数据
function getPageInfoRemote() {

    // 调用$.ajax()函数发送请求并接受$.ajax()函数的返回值
    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "async": false,
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "dataType": "json"
    });
    // 判断当前响应状态码是否为200
    var statusCode = ajaxResult.status;

    // 如果当请响应状态码不是200 说明发生了错误或其他意外情况，显示错误提示信息
    if (statusCode != 200) {
        layer.msg("失败！,响应状态码=" + statusCode + " 说明信息：" + ajaxResult.statusText);
        return null;
    }

    // 若果状态码是200 说明处理成功，获取PageInfo   responseJSON代表着返回值ResultEntity
    var resultEntity = ajaxResult.responseJSON;

    // 从resultEntity中获取result属性
    var result = resultEntity.result;

    // 判断result的值
    if (result == "FAILED") {
        layer.msg(resultEntity.errorMessage);
        return null;
    }

    // result为SUCCESS时，获取PageInfo
    var pageInfo = resultEntity.data;
    return pageInfo;
}

// 填充表格
function fillTableBody(pageInfo) {
    // 清除tbody中旧的内容
    $("#rolePageBody").empty();

    // 没有查到数据时 将分页条也清除掉
    $("#Pagination").empty();

    // 1. 判断pageInfo对象是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' style='text-align:center;'>抱歉！没有查询到您查询的数据！</td></tr>")
        return;
    }

    // 2. 使用pageInfo的list属性填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {

        // 从PageInfo中获取每个role对象
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;

        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkBoxTd = "<td><input id='" + roleId + "' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";

        var checkBtn = "<button id='" + roleId + "' type='button' class='btn btn-success btn-xs checkBtn'><i class='glyphicon glyphicon-check'></i></button>";
        var editBtn = "<button id='" + roleId + "' type='button' class='btn btn-primary btn-xs editBtn'><i class='glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='" + roleId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class='glyphicon glyphicon-remove'></i></button>";
        var authBtn = "<button id='" + roleId + "' type='button' class='btn btn-info btn-xs authBtn'><i class='glyphicon glyphicon-cog'></i></button>";

        var buttonTd = "<td>" + checkBtn + " " + editBtn + " " + removeBtn + " " + authBtn + "</td>";

        var tr = "<tr>" + numberTd + checkBoxTd + roleNameTd + buttonTd + "</tr>";

        $("#rolePageBody").append(tr);
    }

    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页导航条
function generateNavigator(pageInfo) {

    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        num_edge_entries: 3, // 边缘页数
        num_display_entries: 5, // 主体页数
        callback: paginationCallBack, // 指定用户点击"翻页"的按钮时跳转页面的回调函数
        items_per_page: pageInfo.pageSize, // 每页显示多少条数据
        current_page: pageInfo.pageNum - 1, // Pagination内部属性"current_page"默认是0，表示第1页,PageInfo内部属性"pageNum"默认值为1，所以减1
        prev_text: '上一页', // 上一页按钮上显示的文本
        next_text: '下一页' // 下一页按钮上显示的文本
    }

    // 调用pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数
function paginationCallBack(pageIndex, JQuery) {

    $("#checkedBox").prop("checked", false);

    // 修改window对象的pageNum属性
    window.pageNum = pageIndex + 1;

    // 调用分页函数
    generatePage();
    return false;
}