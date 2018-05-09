<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="g-hd">
    <div class="g-lf">
        <img id="logoImg" src="${basePath}/static/global/images/logo.png" alt="成都市中小微企业运行情况分析系统" style="cursor: pointer;float: left;margin-left: 10px;margin-top: 6px;width: 350px;height: 40px">
        <%--<a href="${basePath}/index.do" style="float: left;margin-left: -45px;margin-top: 8px">优易智慧产业地图</a>--%>
    </div>
    <div class="m-nav">
        <ul>
            <li hs-active="index"><a href="${basePath}/index.do">产业分布</a></li>
            <li hs-active="heatMap"><a href="${basePath}/heatMap.do" >活力分析</a></li>
            <li hs-active="change"><a href="${basePath}/change.do">运行分析</a></li>
        </ul>
        <span class="corner corner-l"></span><span class="corner corner-r"></span>
    </div>
</div>