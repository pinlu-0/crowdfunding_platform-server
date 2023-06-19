<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: 蜡笔小新
  Date: 2022/10/19
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%-- http://localhost:8080/crowdfunding02-admin-webui/test/ssm.html
        端口号前面的":"不能省略
        ${pageContext.request.serverName}：获取主机名
        ${pageContext.request.serverPort}：获取端口号
        ${pageContext.request.contextPath}：获取项目路径（前面不能带"/" 后面必须带“/”）
        页面上所有参考base标签的标签都必须放在base标签的后面
        页面上所有参考base标签的标签的路径都不能以“/”开头
        http//${pageContext.request.serverName}:${pageContext.request.serverPort}
    --%>

    <%-- https://code.jquery.com/jquery-3.5.1.min.js --%>
    <script  type="text/javascript" src="jquery/jquery-3.1.1.js"></script>
    <%--依赖jquery的资源要放到jquery后边--%>
    <script type="text/javascript" src="layer-v3.1.1/layer/layer.js"></script>

    <base href="${pageContext.request.contextPath}/">

    <script type="text/javascript">

        $(function () {

            /*探究AJax工作原理*/
            $("#asyncBtn").click(function () {
                console.log("Ajax函数之前");
                $.ajax({
                    "url": "test/ajax/async.html",
                    "type": "post",
                    "async": false, // 关闭异步工作模型，使用同步方式工作。此时，所有在同一个线程内按顺序执行
                    "success": function (response) {
                        // success() 函数是接收到服务器端响应后执行
                        console.log("回调函数内部：" + response);
                    }
                });
                // 在$.ajax()后执行，不等待success()
                console.log("Ajax函数之后");
            });


            /*// 测试Layer弹框
            $("#btn3").click(function () {
                layer.msg("Layer的弹框！");
            });


            /!*使用@RequestBody接收复杂对象*!/
            // 定义JSON对象
            var student = {
                "stuId": 5,
                "stuName": "tom",
                "address": {
                    "province": "河南省",
                    "city": "郑州市",
                    "street": "和平街"
                },
                "subjectList": [
                    {
                        "subjectName": "JavaEE",
                        "subjectScore": "80"
                    }, {
                        "subjectName": "SSM",
                        "subjectScore": "99"
                    }
                ],
                "map": {
                    "k1": "v1",
                    "k2": "v2"
                }
            };
            // 处理JSON对象为JSON字符串
            var studentStr = JSON.stringify(student);
            $("#btn2").click(function () {
                $.ajax({
                    "url": "send/compose/object.json",
                    "type": "POST",
                    "data": studentStr,
                    "contentType": "application/json;charset=utf-8",
                    "dataType": "json",
                    "success": function (response) {
                        console.log(response);
                    },
                    "error": function (response) {
                        console.log(response);
                    }
                });
            });

            /!*测试后端使用@RequestBody接收JSON对象*!/
            //定义JSON数组
            var array = [5, 8, 12];
            // 将JSON数组转换成JSON字符串
            var jsonStr = JSON.stringify(array);
            $("#btn1").click(function () {
                $.ajax({
                    "url": "send/array.html",    // 请求目标
                    "type": "POST",              // 请求方式
                    //"data":{"array":[5,8,12]},            // 请求数据
                    //"data":{"array[]":5,"array[]":8,"array[]":12},      //这种方式是实体类中有一个 List<Integer> array; 属性
                    "data": jsonStr,
                    "contentType": "application/json;charset=utf-8",          // 响应数据类型
                    "success": function (response) { // 服务器端成功处理请求后调用的回调函数
                        alert(response);
                    },
                    "error": function (response) {  // 服务器端处理请求失败后调用的回调函数
                        alert(response);
                    }
                });
            });*/
        });
    </script>
</head>
<body>

<button id="asyncBtn">测试Ajax异步工作原理</button>
<br/>
<br/>

<a href="test/ssm.html">测试ssm整合环境</a>

<br/>
<br/>

<button id="btn1">Send Array</button>

<br/>
<br/>

<button id="btn2">Send Compose Object</button>

<br/>
<br/>

<button id="btn3">点我弹框</button>
</body>
</html>
