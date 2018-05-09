<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>成都市中小微企业运行情况分析系统 - 企业管理</title>
    <jsp:include page="/common/base_tags.jsp" flush="true"/>
    <script type="text/javascript" src="${basePath}/static/js/manage/enterprise_validate.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/base/jquery.form.min.js"></script>
    <%--<link href="${basePath}/images/favicon.ico" rel="shortcut icon" type="image/x-icon">--%>
    <link rel="stylesheet" href="${basePath }/static/css/enterprise/enterprise.css">
    <script>
        var focus="company";
//        company floor street import indexSet chart
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
            <div class="page-content st-navi">
                <div class="page-header">
                    <h1>
                        <small>
                            <i class="icon-double-angle-right"></i>
                            企业管理
                        </small>
                    </h1>
                </div><!-- /.page-header -->

                <div class="row" id="enterprise">
                    <div class="col-xs-12">
                        <form action="${basePath }/enterprise/aEnterprise/search.do" method="post"  id="search" name="searchForm">
                            企业注册号 ：<label>
                            <input class="form-control input-insert" name="params.registerNumber" type="text" placeholder="请输入企业注册号">
                        </label>
                            企业名称：<label>
                            <input class="form-control input-insert" name="params.name" type="text" placeholder="请输入企业名称">
                        </label>
                            企业地址：<label>
                            <input class="form-control input-insert" name="params.registerAddress" type="text" placeholder="请输入企业地址">
                        </label>
                            <span>当前数据信息:</span><select name="params.uploadDate" id="uploadDate">
                            <option value="">全部信息</option>
                            <option value="1">最新上传信息</option>
                        </select>
                            <button id="search-button"   type="button" class="btn btn-info btn-xs btn-search" >
                                <i class="icon-search bigger-110"></i> 搜索
                            </button>
                        </form>
                        <hr>
                        <form action="" id="form1" name="form1" method="POST">
                            <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <input type="hidden" class="ace" />
                                    <th class="center">企业注册号</th>
                                    <th class="center">企业名称</th>
                                    <th class="center">企业地址</th>
                                    <th class="center" colspan="2">操作</th>
                                </tr>
                                </thead>

                                <tbody id="tbody">
                                    <div id="hiddenClass"  style="margin-left: 720px;margin-top: 140px;position: absolute;font-size: 20px;display: none">暂无所查找的企业信息</div>
                                </tbody>
                            </table>
                        </form>
                    </div><!-- /.table-responsive -->
                        <div id="pageClass">
                            <jsp:include page="/common/bottom_page.jsp"></jsp:include>
                        <div>
                </div><!-- /span -->
            </div><!-- /row -->
            <jsp:include page="/common/time.jsp" flush="true" />
            <%@include file="/common/dialog.jsp" %>
        </div>
    </div>
</div>
<script src="${basePath}/static/js/manage/enterprise.js"></script>
</body>
<jsp:include page="/common/bottom.jsp" flush="true"/>
</html>