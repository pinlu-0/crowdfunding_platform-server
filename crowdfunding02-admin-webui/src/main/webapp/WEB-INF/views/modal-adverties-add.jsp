<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal fade" id="addAdvertiseModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="exampleModalLabel">添加广告</h4>
            </div>
            <div class="modal-body">
                <form id="uploadForm">
                    <div class="form-group">
                        <label class="control-label">广告描述：</label>
                        <input type="text" id="desc" name="name" class="form-control" placeholder="请输入广告描述">
                    </div>

                    <div class="form-group">
                        <label class="control-label">选择广告：</label><br>
                        <button class="btn btn-success" type="button" id="choiceFileBtn"><i class="glyphicon glyphicon-arrow-up"></i>上传文件</button>
                        <button id="previewImg" type="button" class='btn btn-success'><i class='glyphicon glyphicon-eye-open'></i>预览</button>
                        <input type="file" id="file" name="file" style="display: none">
                    </div>
                    <div id="preview" class="form-group">
                        <%-- 图片预览 --%>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
                <button type="button" id="sureAddBtn" class="btn btn-primary">确认添加</button>
            </div>
        </div>
    </div>
</div>
