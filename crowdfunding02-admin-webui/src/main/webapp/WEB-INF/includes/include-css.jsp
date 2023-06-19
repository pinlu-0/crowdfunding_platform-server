<%@ page contentType="text/html;charset=UTF-8" %>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<base href="${pageContext.request.contextPath}/">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/main.css">
<style>
    .tree li {
        list-style-type: none;
        cursor: pointer;
    }

    .tree-closed {
        height: 40px;
    }

    .tree-expanded {
        height: auto;
    }
    /* 表格样式 */
    th {
        text-align: center;
    }

    td {
        text-align: center;
    }
</style>

