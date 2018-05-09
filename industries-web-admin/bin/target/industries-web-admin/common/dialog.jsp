<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- #dialog-confirm -->
<div id="youeConfirm" class="hide">
	<div class="center">
		<h4><i class="icon-hand-right blue bigger-120"></i> <span id="youeConfirmStr"></span></h4>
	</div>
</div>
<div id="youeInfo" class="hide">
	<div class="center">
		<h4><i class="icon-info-sign blue bigger-120"></i> <span id="youeInfoStr"></span></h4>
	</div>
</div>
<div id="youeError" class="hide">
	<div class="center">
		<h4><i class="icon-warning-sign red bigger-120"></i> <span id="youeErrorStr"></span></h4>
	</div>
</div>
<div id="youeLoading" class="hide">
	<div class="center">
		<h4><img alt="loading" src="<c:url value="/static/images/loading.gif"/>"><span id="youeLoadingStr"></span></h4>
	</div>
</div>