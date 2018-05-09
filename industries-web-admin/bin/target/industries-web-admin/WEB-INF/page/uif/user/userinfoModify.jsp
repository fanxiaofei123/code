<%@ page language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>用户列表</title>
    <jsp:include page="/common/base_tags.jsp" flush="true"/>
    <link rel="stylesheet" href="${basePath }/static/css/common.css">

    <script type="text/javascript" src="${basePath }/static/js/uif/user_validate.js"></script>
    <script type="text/javascript" src="${basePath }/static/js/uif/info_modify.js"></script>
    <script>
        var focus = "chart";
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
                            账户密码修改
                        </small>
                    </h1>

                </div>
                <div class="col-sm-6 col-sm-offset-1">
                    <div class="row">
                        <div class="col-xs-12">

                            <div class="widget-main">
                                <h4 class="header blue lighter bigger">请输入你的新旧密码</h4>
                                <div class="space-6"></div>
                                <form id="userInfoModify" class="form-horizontal" action="${basePath}/" method="post"
                                      role="form">
                                    <div class="form-group ud-gp">
                                        <label class="col-sm-2 control-label">旧密码</label>
                                        <div class="col-sm-6 input-icon input-icon-right">
                                            <input type="password" class="form-control ud-pwd-md" name="userPassword"
                                                   id="oldPassword"
                                                   placeholder="请输入旧密码"
                                                   onkeyup="this.value=this.value.replace(/[^\w]/g,'');"/>
                                            <i class="icon-lock"></i>
                                            <span class="oldPwd"></span>
                                        </div>

                                    </div>
                                    <div class="form-group ud-gp">
                                        <label class="col-sm-2 control-label">新密码</label>
                                        <div class="col-sm-8 input-icon input-icon-right">
                                            <input type="password" class="form-control ud-pwd-md"
                                                   name="userPasswordNew" placeholder="请输入新密码"
                                                   onkeyup="this.value=this.value.replace(/[^\w]/g,'');">
                                            <i class="icon-lock"></i>
                                        </div>
                                        <%--<label id="passwdTips" class="col-sm-8" style="display: none;">--%>
                                        <%--<div><span class="passwdTips"></span>6-20位字符</div>--%>
                                        <%--<div><span class="passwdTips"></span>只能包含大小写字母.数字以及标点符号除(空格)</div>--%>
                                        <%--<div><span class="passwdTips"></span>大写字母.小写字母.数字和标点符号至少包含2种</div>--%>
                                        <%--</label>--%>
                                        <div class="col-sm-6 col-sm-offset-2">6-20位字符，大小写字母和数字任意组合。</div>
                                    </div>

                                    <div class="form-group ud-gp">
                                        <label class="col-sm-2 control-label">确认密码</label>
                                        <div class="col-sm-6 input-icon input-icon-right">
                                            <input type="password" class="form-control ud-pwd-md"
                                                   name="userPasswordConfirm" placeholder="确认密码"
                                                   onkeyup="this.value=this.value.replace(/[^\w]/g,'');">
                                            <i class="icon-lock"></i>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-4">
                                            <button id="loginBtn" type="button" onclick="confirmMdify()"
                                                    class="width-35 pull-right btn btn-sm btn-primary">
                                                <i class="icon-key"></i> 修改
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div><!-- /span -->

                    </div><!-- /row -->
                </div>

            </div>

        </div>
    </div>
</div>
<jsp:include page="/common/time.jsp" flush="true"/>
<%@include file="/common/dialog.jsp" %>
<link rel="stylesheet" href="${basePath }/static/css/userInfo/userInfo.css">
</body>
<jsp:include page="/common/bottom.jsp" flush="true"/>
</html>
