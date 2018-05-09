<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0"/>
    <title>成都市中小微企业运行情况分析系统 - 数据导入 - 企业运行数据导入</title>
    <jsp:include page="/common/base_tags.jsp" flush="true"/>
    <script>
        var focus="import";
    </script>
</head>
<body>
<jsp:include page="/common/head.jsp" flush="true"/>
<div class="main-container" id="main-container">
    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <%@include file="/common/menu.jsp" %>
        <div class="main-content">
            <div class="page-content">
                <div class="page-header">
                    <h1>
                        <small>
                            <i class="icon-double-angle-right"></i>
                            数据导入
                        </small>
                        <small>
                            <i class="icon-double-angle-right"></i>
                            企业运行数据导入
                        </small>
                    </h1>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="widget-box">
                            <div class="widget-header widget-header-flat">
                                <h4 class="smaller">
                                    上传数据
                                </h4>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div class="form-group">
                                                <input id="uploadFile" type="file" accept=".csv,.xls,.xlsx"
                                                       style="width: 300px;height: inherit;margin:0px;display:inline;" class="form-control">
                                                <button type="button" class="btn btn-default" id="uploadButton">上传</button>
                                                <h5 style="color: red;padding: 10px 0 0 0">注：仅支持xls、xlsx格式的文件</h5>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                    <address id="messageSpan"></address>
                                    <div>
                                        <h5>下载表格模板</h5>
                                        <h5 style="text-indent:2em;">优易数据请<a href="${basePath}/tableDownload/企业运行数据表（优易）.xls">点击下载。</a></h5>
                                        <h5 style="text-indent:2em;">管理员请 <a href="${basePath}/tableDownload/企业运行数据表（管理员）.xls">点击下载。</a></h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<style>
    .tb tr {
        height: 100px;
    }

    table tr td {
        width: 280px;
        height: 70px;
        cursor: pointer;
    }
</style>
</body>
<script src="${basePath}/static/js/dataImport/enterpriseOperationDataUpdates.js"></script>
<jsp:include page="/common/bottom.jsp" flush="true"/>
</html>