<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>成都市中小微企业运行情况分析系统 - 企业管理-数据更新</title>
	<jsp:include page="/common/base_tags.jsp" flush="true"/>
	<script type="text/javascript" src="${basePath}/static/js/base/jquery.form.min.js"></script>
	<script type="text/javascript" src="${basePath}/static/js/base/jquery.validate.min.js"></script>
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
						<small onclick="window.location.href='${basePath}/enterprise/list.do'" style="cursor: pointer">
							<i class="icon-double-angle-right"></i>
							数据更新
						</small>
					</h1>
				</div><!-- /.page-header -->
<div class="row"  id="updateEn">
	<%--数据更新左边--%>
	<div class="col-xs-4" style="float: left">
		<h4>企业注册号:&nbsp;&nbsp;&nbsp;<span>${enterpriseBase.registerNumber}</span></h4>
		<br>
		<h4>企业名称:&nbsp;&nbsp;&nbsp;<span>${enterpriseBase.name}</span></h4>
		<br/>
		<h4>行业代码:&nbsp;&nbsp;&nbsp;<span>${enterpriseBase.industryCode}</span></h4>
	</div>
	<%--数据更新右边--%>
		<input id="updateFalg"   type="hidden" value="${flagUpdate}" />
	<form  id="updateEnForm" >
		<input type="hidden" id="enterpriseId" name="enterpriseId" value="${enterpriseBase.id}">
		<input type="hidden" id="sourceLogId" name="sourceLogId" value="${enterpriseBase.sourceLogId}">
		<div class="col-xs-8"  style="float: left">
			<table id="myEnTable" class="build-table">
				<tr class="tr-en-set" style="display: block;margin: 8px 0">
					<td><span class="selectClass">产业</span></td>
					<td style="padding-left: 13px">
						<select id="addEnIndustry" style="background-color: #E8E8E8"  name="industryId" onchange="myIndustry()" disabled="disabled" selected="true">
							<c:forEach var="categoryIn" items="${categoryIndustry}">
							<c:if test="${categoryIn.id == industry.id}">
								<option value="${categoryIn.id}" selected="true">${categoryIn.name}</option>
							</c:if>
							<c:if test="${categoryIn.id != industry.id}">
								<option value="${categoryIn.id}">${categoryIn.name}</option>
							</c:if>
							</c:forEach>
						</select>

					</td>
				</tr>
				<tr class="tr-en-set" style="display: block;margin: 8px 0">
					<td>
						<span class="selectClass">行业</span>
					</td>
					<td style="padding-left: 13px">
						<select id="addTrade" name="tradeId" style="background-color: #E8E8E8" onclick="myTrade()" disabled="disabled" selected="true">
							<c:forEach items="${tradeIds}" var="tradeId">
							<c:if test="${tradeId.id == trade.id}">
								<option value="${tradeId.id}" selected="true">${tradeId.name}</option>
							</c:if>
								<c:if test="${tradeId.id != trade.id}">s
									<option value="${tradeId.id}">${tradeId.name}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="tr-en-set" style="display: block;margin: 8px 0">
					<td>
						<span class="selectClass">门类</span>
					</td>
					<td style="padding-left: 13px">
						<select id="addCategory" style="background-color: #E8E8E8" name="categoryId" disabled="disabled" selected="true">
							<c:forEach var="categoryId" items="${categoryIds}">
							<c:if test="${categoryId.id == category.id}">
								<option value="${categoryId.id}" selected="true">${categoryId.name}</option>
							</c:if>
								<c:if test="${categoryId.id != category.id}">
									<option value="${categoryId.id}">${categoryId.name}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="tr-en-set" >
					<td>
               <span>
                   从业人数</span>
					</td>
					<td style="padding-left: 13px">
						<input name="employeeCount" value="${enterpriseBase.employeeCount}" id = "employeeCount" type="text" class="form-control input-insert-modal" disabled>
					</td>
				</tr>
				<tr class="tr-en-set" >
					<td>
						<span  for="news" class= "publicSentimentLabel">
							联系电话</span>
					</td>
					<td style="padding-left: 13px">
						<input name="phoneNumber" id = "phoneNumber"  value="${enterpriseBase.phoneNumber}" type="text" class="form-control input-insert-modal" disabled>
					</td>
				</tr>
				<tr class="tr-en-set" >
					<td>
               <span for="news" class= "publicSentimentLabel">
                   注册日期</span>
					</td>
					<td style="padding-left: 13px">
						<input name="registerTime" value="${enterpriseBase.registerTime}"  id = "registerTime"  type="date" class="form-control input-insert-modal" disabled>
					</td>
				</tr>
				<tr class="tr-en-set" >
					<td>
               <span for="news" class= "publicSentimentLabel">
                   注册资金</span><div>（万元）</div>
					</td>
					<td style="padding-left: 13px">
						<input name="registerCapital"  value="${enterpriseBase.registerCapital}"  id = "registerCapital"  type="text" class="form-control input-insert-modal" disabled>
					</td>
				</tr>
				<tr class="tr-en-set" >
					<td>
               <span for="news" class= "publicSentimentLabel selectClass">
                   状态
               </span>
					</td>
					<td style="padding-left: 13px">
						<select id = "openFlag" style="background-color: #E8E8E8" disabled name="openFlag">
								<option <c:if test="${enterpriseBase.openFlag==null}">selected="true"</c:if>>未选择</option>
								<option value="0" <c:if test="${enterpriseBase.openFlag==0}">selected="true"</c:if>>停业</option>
								<option value="1" <c:if test="${enterpriseBase.openFlag==1}">selected="true"</c:if>>营业</option>
						</select>
					</td>
				</tr>
				<tr class="tr-en-set">
					<td>
               <span for="blogForum"  class="publicSentimentLabel selectClass">
                   地址</span>
					</td>
					<td style="padding-left: 13px">
						<input name="address" value="${enterpriseBase.address}" id="address" type="text" class="form-control input-insert-modal" disabled>
					</td>
				</tr>
				<tr style="display: block;margin: 8px 0">
					<td><span>所在街道</span></td>
					<td style="padding-left: 13px">
						<select id="addEnStreet" name="streetId" style="background-color: #E8E8E8" onchange="myEnStreet()" disabled="disabled" selected="true">
							<c:forEach items="${list}" var="street">
								<c:if test="${street.id == enterpriseBase.streetId}">
									<option value="${street.id}" selected="true">${street.name}</option>
								</c:if>
								<c:if test="${street.id != enterpriseBase.streetId}">
									<option value="${street.id}">${street.name}</option>
								</c:if>
							</c:forEach>
							<c:if test="${enterpriseBase.streetId==null}">
								<option value="" selected="selected">无</option>
							</c:if>
						</select>
					</td>
				</tr>
				<tr style="display: block;margin: 8px 0">
					<td><span>所在社区</span></td>
					<td style="padding-left: 13px">
						<select id="addEnCommunity" style="background-color: #E8E8E8" name="communityId" disabled="disabled" selected="true">
							<c:if test="${communitys== null || fn:length(communitys) == 0}">
								<option value="" selected="true">无</option>
							</c:if>

							<c:if test="${communitys!= null || fn:length(communitys) != 0}">
							<c:forEach items="${communitys}" var="newCommunity">
								<c:if test="${newCommunity.communityId == enterpriseBase.communityId}">
									<option value="${newCommunity.communityId}" selected="true">${newCommunity.communityName}</option>
								</c:if>
								<c:if test="${newCommunity.communityId != enterpriseBase.communityId}">
									<option value="${newCommunity.communityId}">${newCommunity.communityName}</option>
								</c:if>
							</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr class="tr-en-set" >
					<td>
               <span>
                   主营业务</span>
					</td>
					<td style="padding-left: 13px">
						<input id="majorBusiness" name="majorBusiness" value="${enterpriseBase.majorBusiness}"   type="text" class="form-control input-insert-modal" disabled>
					</td>
				</tr>
			</table>
			<span class="restBtn" style="display: none;float: left;margin-left: 233px"><button type="button" class="btn btn-primary btn-xs" value="取消" onclick="cancelEnterpriseBtn()">取消</button></span>
			<span class="addBtn" style="display: none;float: left;margin-left: 2px"><button type="button" onclick="formSubmit()" id="btnUpdate" class="btn btn-primary btn-xs">更新</button></span>
			<span class="colseBtn" style="float: left;margin-left: 233px"><button type="button" class="btn btn-primary btn-xs" value="关闭" onclick="closeEnterpriseBtn()">关闭</button></span>
            <span class="editBtn" style="float: left;margin-left: 2px"><button type="button" id="btnOK" class="btn btn-primary btn-xs" onclick="enterpriseEditClick()">编辑</button></span>

		</div>
	</form>
</div>
			</div><!-- /row -->
			<jsp:include page="/common/time.jsp" flush="true" />
			<%@include file="/common/dialog.jsp" %>
			<link rel="stylesheet" href="${basePath }/static/css/enterprise/enterprise.css">
		</div>
	</div>
</div>
<script src="${basePath}/static/js/manage/enterprise.js"></script>
<script src="${basePath}/static/js/manage/street.js"></script>
</body>
<jsp:include page="/common/bottom.jsp" flush="true"/>
</html>