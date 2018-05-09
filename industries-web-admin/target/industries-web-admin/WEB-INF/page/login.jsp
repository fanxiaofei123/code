<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0"/>
    <jsp:include page="/common/base_tags.jsp" flush="true"/>
    <script src="${basePath}/static/js/login/login_validate.js"></script>
    <script src="${basePath}/static/js/login/login.js"></script>
</head>
<body class='login-layout login-bg'>
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="center">
                        <h3 style="padding: 40px 0 20px 0;"><div class="red">成都市中小微企业运行情况分析系统</div></h3>
                    </div>
                    <div class="space-6"></div>
                    <div class="position-relative">
                        <div id="login-box"
                             class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">请输入您的信息 </h4>
                                    <div class="space-6"></div>
                                    <form id="updateData" method="post">
                                        <fieldset>
                                            <label class="block clearfix"> <span
                                                    class="block input-icon input-icon-right"> <input
                                                    type="text" id="name" name="name" value="admin"
                                                    required="required" maxlength="16" class="form-control"
                                                    placeholder="请输入用户名"/><i class="icon-user"></i>
												</span>
                                            </label> <label class="block clearfix"> <span
                                                class="block input-icon input-icon-right"> <input
                                                type="password" id="userPassword" name="userPassword"
                                                required="required" maxlength="16" class="form-control"
                                                placeholder="请输入密码"/><i class="icon-lock"></i>
												</span>
                                        </label> <label class="block clearfix"> <span> <input
                                                class="col-xs-5" id="verifyCode" name="verifyCode"
                                                maxlength="4" type="tel"
                                                class="form-control" placeholder="请输入验证码"/>
												</span> <span class="col-sm-5"> <img id="vimg"
                                                                                     style="cursor:pointer;width:80px;height:30px;"
                                                                                     title="验证码"
                                                                                     width="60" height="37"/>
												</span>
                                        </label>
                                            <div class="space"></div>
                                            <div class="clearfix">
                                                <!-- <label class="inline"> <input type="checkbox" class="ace" /> <span class="lbl"> Remember Me</span></label> -->
                                                <button id="loginBtn" type="submit"
                                                        class="width-35 pull-right btn btn-sm btn-primary">
                                                    <i class="icon-key"></i> 登录
                                                </button>
                                            </div>
                                            <div class="space-4"></div>
                                        </fieldset>
                                    </form>
                                </div>
                                <!-- /widget-main -->
                                <div class="toolbar clearfix">
                                    <div>
                                        <a href="#" onclick="show_box('forgot-box'); return false;"
                                           class="forgot-password-link">
                                            <!-- 												<i class="icon-arrow-left"></i>忘记密码 -->
                                        </a>
                                    </div>
                                    <div>
                                        <a href="#" onclick="show_box('register-box'); return false;"
                                           class="user-signup-link">
                                            <!-- 												 注册<i class="icon-arrow-right"></i> -->
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <!-- /widget-body -->
                        </div>
                        <%@include file="/common/dialog.jsp" %>
                    </div>
                    <!-- /position-relative -->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>