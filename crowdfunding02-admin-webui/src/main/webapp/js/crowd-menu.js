


/**
 * 查询构成权限菜单树的数据
 */
function generateTree() {
    // 生成zTree属性结构的JSON数据,数据的来源是发送Ajax请求查询到的
    $.ajax({
        "url": "menu/get/whole/tree.json",
        "type": "POST",
        "dataType": "json",
        "success": function (response) {
            var result = response.result;
            if (result == "SUCCESS") {
                // 获取从后端返回的数据
                var zNodes = response.data;
                // 声明一个JSON数组用来存储对zTree所做的设置
                var setting = {
                    "view": {
                        "addDiyDom": addDiyDemo,
                        "addHoverDom": addHoverDom,
                        "removeHoverDom": removeHoverDom
                    },
                    "data": {
                        "key": {
                            // url的值要设置成zTree中不存在的值  才不会跳转
                            "url": ""
                        }
                    }
                };
                // 生成树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (result == "FAILED") {
                layer.msg(response.errorMessage);
            }
        },
        "error": function (response) {
            layer.msg(response.status + " " + response.statusText);
        }
    });

}



/**
 * 鼠标移入节点范围内动态生成按钮
 * @param treeId
 * @param treeNode
 */
function addHoverDom(treeId, treeNode) {
    // 按钮组的标签结构<span><a><i></i></a></span>
    // 按钮组出现的位置：节点中treeDemo_n_a超链接的后面
    // 为了在需要移除按钮组的时候能够精确定位到按钮组所在span，需要给span设置有规律的id = btnGroup
    var spanId = treeNode.tId + "_btnGrp";
    // 判断是否已经添加过了
    if ($("#" + spanId).length > 0) {
        return;
    }
    // 准备各个按钮组的超链接
    var addBtn = "<a id='" + treeNode.id + "' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='添加节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='" + treeNode.id + "' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='" + treeNode.id + "' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
    // 获取当前节点的级别数据
    var level = treeNode.level;
    var btnHTML = "";
    /**
     * 判断当前节点的级别
     */
    if (level == 0) {
        // 根结点 ,可以添加子节点
        btnHTML = addBtn;
    }
    if (level == 1) {
        // 可以加，可以修改
        btnHTML = addBtn + " " + editBtn;
        // 获取当前节点的子节点数量
        var length = treeNode.children.length;
        if (length == 0) {
            // 如果没有子节点 则可以删除
            btnHTML = btnHTML + " " + removeBtn;
        }
    }
    // 叶子结点 可以改 可以删
    if (level == 2) {
        btnHTML = editBtn + " " + removeBtn;
    }

    // 超链接的id
    var anchorId = treeNode.tId + "_a";
    // 执行在超链接后面附加span元素的操作
    $("#" + anchorId).after("<span id='" + spanId + "'>"+btnHTML+"</span>");

}

/**
 * 鼠标移入结点范围内删除按钮
 * @param treeId
 * @param treeNode
 */
function removeHoverDom(treeId, treeNode) {
    // 找到span标签的id
    var spanId = treeNode.tId + "_btnGrp";
    // 移除span
    $("#" + spanId).remove();
}

/**
 * 修改默认的图标
 * @param treeId
 * @param treeNode
 */
function addDiyDemo(treeId, treeNode) {

    // treeId 是整个树形结构附着的ul标签的id
    console.log("treeId=" + treeId);
    // 当前树形结点的全部的数据，包括从后端查询得到的Menu对象的全部属性
    console.log(treeNode);

    // span标签的id  treeDemo_8_ico    treeDemo_8这是tid  只需拼接后边的ico
    var spanId = treeNode.tId + "_ico";
    // 根据控制图标的span标签的id找到这个span标签
    // 删除旧的class属性  添加新的
    $("#" + spanId).removeClass().addClass(treeNode.icon);

}

