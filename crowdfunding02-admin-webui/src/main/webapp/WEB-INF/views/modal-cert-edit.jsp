<%@ page contentType="text/html;charset=UTF-8"%>
<div id="certEditModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" style="font-family:Lucida Calligraphy, cursive, serif, sans-serif;">系统弹窗</h4>
            </div>
            <form>
                <div class="modal-body">
                    <input id="certId" type="hidden" name="id">
                    资质名称：<input id="certName" type="text" name="name"/><br/>
                </div>
                <div class="modal-footer">
                    <button id="certEditBtn" type="button" class="btn btn-default"><i
                            class=" glyphicon glyphicon-pencil"></i> 修改
                    </button>
                    <button id="ResetBtn" type="reset" class="btn btn-primary"><i
                            class="glyphicon glyphicon-refresh"></i> 重置
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
