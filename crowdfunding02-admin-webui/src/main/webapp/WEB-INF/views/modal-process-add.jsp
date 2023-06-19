<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal fade" id="addProcessModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="exampleModalLabel">上传流程定义文件</h4>
            </div>
            <div class="modal-body">
                <form id="uploadForm">
                    <div class="form-group">
                        <button id="choiceFileBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-arrow-up"></i>选择文件</button>
                        <input type="file" id="file" name="file" style="display: none">
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