<%@ page contentType="text/html;charset=UTF-8" %>
<div id="streetFrom" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content   modal-width-400">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">
                    <i class="icon-pencil"></i>
                    <span id="lblAddTitle" style="font-weight:bold"></span>
                </h4>
            </div>
            <form class="form-horizontal form-bordered form-row-strippe" id="ffAdd" action="" data-toggle="validator"
                  enctype="multipart/form-data">
                <div class="modal-body" id="modalBody">
                    <div class="row">
                        <div class="col-md-12" style="display: none">
                            <div class="form-group">
                                <label class="control-label col-md-2">名称</label>
                                <div class="col-md-10">
                                    <input id="id" name="id"   type="text" class="form-control"/>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="control-label col-md-2">名称</label>
                                <div class="col-md-10">
                                    <input id="name" name="name" type="text" class="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group" id="add-street" style="display:none">
                                <label class="control-label col-md-2">街道</label>
                                <div class="col-md-10">
                                    <select id="stId" name="streetId" class="form-control"></select>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>
                <div class="modal-footer bg-info">
                    <input type="hidden" id="objId"/>
                    <input type="hidden" id="streetId"/>
                    <button type="button" onclick="updateOrAdd()" class="btn btn-warning">确定</button>
                    <button type="button" class="btn btn-info" onclick="closeModel()" data-dismiss="modal">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>

