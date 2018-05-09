<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/28
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*,com.youedata.cd.innovation.msg.config.UploadPathConfig" pageEncoding="UTF-8"%>
<%
    String imgServerURL = "";
	imgServerURL = UploadPathConfig.IMAGES_SERVER_URL;
    request.setAttribute("imgServerURL", imgServerURL);
%> 
<script type="text/javascript">
var imgServerURL="${imgServerURL}";
</script>
 