<%--
  Created by IntelliJ IDEA.
  User: honshe
  Date: 2016/7/11
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>成都市中小微企业运行情况分析系统 - 楼宇管理</title>
    <jsp:include page="/common/base_tags.jsp" flush="true"/>
    <%--<link href="${basePath}/images/favicon.ico" rel="shortcut icon" type="image/x-icon">--%>
    <link rel="stylesheet" href="${basePath}/static/css/building/building.css">
    <script>
        var focus="floor";
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
                            <span>楼宇管理</span>
                        </small>
                    </h1>
                </div><!-- /.page-header -->



                <div class="row" id="building">
                    <div class="col-xs-12">
                        <form action="${basePath }/building/abuilding/search.do" method="post"  id="search" name="searchForm">
                            <input name="params.name" type="text" id="buildingName"  placeholder="请输入楼宇名称"/>&nbsp;&nbsp;
                            <input name="params.address" type="text" id="buildingAddress" placeholder="请输入楼宇地址"/>&nbsp;&nbsp;
                            <span>当前数据信息:</span><select name="params.uploadDate" id="uploadDate">
                                <option value="">全部信息</option>
                                <option value="1">最新上传信息</option>
                            </select>
                            <button  id="search-button" type="button" class="btn btn-info btn-xs btn-search">
                                <i class="icon-search bigger-110"></i> 搜索
                            </button>

                        </form>
                        <hr>
                            <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th class="center">序号</th>
                                    <th class="center">楼宇名称</th>
                                    <th class="center">楼宇地址</th>
                                    <th class="center">所在街道</th>
                                    <th class="center">所在社区</th>
                                    <th class="center" colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody id="tbody">
                                        <div id="hiddenBuClass"  style="margin-left: 720px;margin-top: 140px;position: absolute;font-size: 20px;display: none">暂无所查找的楼宇信息</div>
                                </tbody>
                            </table>
                    </div><!-- /.table-responsive -->
                        <div id="pageBuClass">
                            <jsp:include page="/common/bottom_page.jsp"></jsp:include>
                        </div>
                </div><!-- /span -->
                        <jsp:include page="from.jsp"></jsp:include>
                <jsp:include page="/common/time.jsp" flush="true" />
                <%@include file="/common/dialog.jsp" %>
            </div><!-- /row -->
        </div>
    </div>
</div>
<script src="${basePath}/static/js/manage/building.js"></script>
</body>
<jsp:include page="/common/bottom.jsp" flush="true"/>
</html>
