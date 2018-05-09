<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="${basePath }/static/css/common.css">
<div class="navbar navbar-default nvbar-style" id="navbar">
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							成都市中小微企业运行情况分析系统
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">

						<li class="light-blue">
							<a id="setadmin" data-toggle="popover" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" 
								<%--<c:if test="${empty sessionScope.user.headPic}">
									src="${basePath }/static/plugins/assets/avatars/user.jpg"
								</c:if>
								<c:if test="${!empty sessionScope.user.headPic}">
									 src="${sessionScope.user.headPic}"
								</c:if>--%>
									 src="${basePath }/static/plugins/assets/avatars/user.jpg">
								<span class="user-info">
									<small>欢迎您,</small>
									${sessionScope.user.name}
								</span>

								<i class="icon-caret-down"></i>
							</a>

 
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
	</div>	

 