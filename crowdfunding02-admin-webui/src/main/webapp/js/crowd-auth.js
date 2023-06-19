// 根据roleId查询已经拥有的权限

// 分配权限的树

function fillAuthTree(roleId) {
    // 发送请求查询auth相关数据
    var ajaxReturnVal = $.ajax({
        "url": "assign/get/all/auth.json",
        "type": "post",
        "dataType": "json",
        "async": false
    });

    if (ajaxReturnVal.status != 200) {
        layer.msg(ajaxReturnVal.status + " " + ajaxReturnVal.statusText);
        return;
    }

    // 从响应数据中获取auth数据集合，这里没有在服务端进行组装而是交给zTree组装
    var authList = ajaxReturnVal.responseJSON.data;

    var setting = {
        data: {
            simpleData: {
                // 开启简单数据模式
                enable: true,
                // 关联父节点
                pIdKey: "categoryId"
            },
            key: {
                name: "title"
            }
        },
        check: {
            enable: true,
            chkStyle: "checkbox"
        }
    };

    // 生成树形结构
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);

    // 获取zTree对象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

    // 让所有节点都展开
    zTreeObj.expandAll(true);

    /**
     * 根据roleId查询当前角色拥有的所有权限
     * @type {*|{readyState, setRequestHeader, getAllResponseHeaders, getResponseHeader, overrideMimeType, abort}|{readyState, getResponseHeader, getAllResponseHeaders, setRequestHeader, overrideMimeType, statusCode, abort}}
     */
    var returnVal = $.ajax({
        "url": "assign/get/assigned/auth/id/by/role/id.json",
        "type": "post",
        "data": {
            "roleId": roleId
        },
        "dataType": "json",
        "async": false
    });
    if (returnVal.status != 200) {
        layer.msg(returnVal.status + " " + returnVal.statusText);
        return;
    }
    var authIdArray = returnVal.responseJSON.data;

    // 根据authIdArray把属性结构中对应的结点勾选上
    for (var i = 0; i < authIdArray.length; i++) {

        var authId = authIdArray[i];

        // 需要勾选 或 取消勾选 的节点数据
        var treeNodes = zTreeObj.getNodeByParam("id", authId);
        
        // 表示勾选节点
        var checked = true;

        // 表示只修改此节点勾选状态，无任何勾选联动操作;防止为了把不该勾选的勾选上
        var checkTypeFlag = false;

        // 勾选 或 取消勾选 单个节点
        zTreeObj.checkNode(treeNodes, checked, checkTypeFlag);
    }


}

