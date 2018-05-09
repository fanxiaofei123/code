<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    int port = request.getServerPort();
    String basePath = "";
    if(port==80){
        basePath = request.getScheme()+"://"+request.getServerName()+path;
    }else{
        basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    }
    request.setAttribute("basePath", basePath);
%>
<link rel="stylesheet" href="${basePath}/static/global/input.css">
<link rel="stylesheet" href="${basePath}/static/global/global.css">
<link rel="stylesheet" href="${basePath}/static/global/style.css">
<link rel="stylesheet" href="${basePath}/static/global/skin.css">
<link rel="stylesheet" href="${basePath}/static/global/4.2.0/css/font-awesome.css">
<link href="${basePath}/images/favicon.ico" rel="shortcut icon" type="image/x-icon">

<script type="text/javascript">var basePath="${basePath}";</script>
