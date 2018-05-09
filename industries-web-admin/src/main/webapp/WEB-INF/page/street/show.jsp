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
    <title>成都市中小微企业运行情况分析系统 - 街道管理</title>
    <jsp:include page="/common/base_tags.jsp" flush="true"/>
    <script type="text/javascript" src="${basePath }/static/js/street/list.js"></script>
    <script type="text/javascript" src="${basePath }/static/js/street/street_validate.js"></script>
    <%--<link href="${basePath}/images/favicon.ico" rel="shortcut icon" type="image/x-icon">--%>
    <link rel="stylesheet" href="${basePath }/static/css/street/street.css">
    <script>
        var focus="street";
        //        company floor street import indexSet chart
    </script>
</head>
<body>
<jsp:include page="/common/head.jsp" flush="true"/>
<style type="text/css">
    .date-s {
        font-size: larger;
    }

    .btn-u-d {
        width: 100px !important;
        text-align: right !important;
    }

</style>
<div class="main-container" id="main-container">
    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <%@include file="/common/menu.jsp" %>
        <div class="main-content">
            <div class="page-content">
                <div id="st-navi">
                    <div class="page-header">
                        <h1>
                            <small>
                                <i class="icon-double-angle-right"></i>
                                <span>街道管理</span>
                            </small>
                        </h1>
                    </div><!-- /.page-header -->

                    <div class="page-header">
                        <h1>
                            <small>
                                街道列表
                            </small>
                            <a href="javascript:void(0)" onclick="addModel()">+新增街道</a>
                        </h1>

                    </div>
                </div>

                <table width="100%">
                    <tbody id="tbody"></tbody>
                </table>
            </div>
        </div><!-- /span -->
    </div><!-- /row -->
</div>
</div>
</div>
<jsp:include page="from.jsp"></jsp:include>
<%@include file="/common/dialog.jsp" %>
</body>
<jsp:include page="/common/bottom.jsp" flush="true"/>
</html>
