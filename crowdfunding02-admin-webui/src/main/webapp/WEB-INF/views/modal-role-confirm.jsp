<%--
  Created by IntelliJ IDEA.
  User: 蜡笔小新
  Date: 2022/10/24
  Time: 8:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 确认模态框 --%>
<div id="confirmModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">系统弹窗</h4>
            </div>
            <div class="modal-body">
                <h5>请确认是否删除如下信息：</h5>
                <div id="roleNameDiv" style="text-align: center"></div>
            </div>
            <div class="modal-footer">
                <button id="removeRoleBtn" style="display:block;margin: 0 auto" type="button" class="btn btn-primary">确认删除</button>
            </div>
        </div>
    </div>
</div>