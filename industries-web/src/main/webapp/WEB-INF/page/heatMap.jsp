<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成都市中小微企业运行情况分析系统 - 活力分析</title>
    <jsp:include page="global/global.jsp"/>
    <link rel="stylesheet" href="${basePath}/static/global/heatMapstyle.css">
</head>
<body data-identity="heatMap">
<jsp:include page="global/header.jsp"/>
<div class="g-sd">
    <div class="g-sdc title">
        <div class="u-rst f-cb">
            <div class="range_time">
                <img src="${basePath}/static/global/images/time.png" style="float: left;
    position: relative;padding-left: 10px">
                <div class="u-sch-item">
                        <span class="u-btn u-btn-c4 u-btn-c5" style="font-size: small">
                            <em id="selectYear" data-id=""></em>
                        </span>
                    <div class="u-sch-item-sel u-sch-item-1 version-select u-sch-time" id="startTime1">
                    </div>
                </div>
                <div class="version-wave" style="font-size: small"></div>
                <div class="u-sch-item">
                        <span class="u-btn u-btn-c4 u-btn-c5" style="font-size: small">
                            <em id="selectQuarter" data-id=""></em>
                        </span>
                    <div class="u-sch-item-sel u-sch-item-1 version-select u-sch-time">
                        <a>第一季度</a>
                        <a>第二季度</a>
                        <a>第三季度</a>
                        <a>第四季度</a>
                    </div>
                </div>
            </div>
            <div class="version-confirm" onclick="myPlaySelectedVersion()">确定
            </div>
            <span id="reminder"></span>
        </div>
    </div>

    <div class="version-show">
        <div class="result">&nbsp;搜索结果:</div>
        <span>&nbsp;为您找到</span>
        <span id="enterpriseNumber">&nbsp;</span>
        <span>家企业</span>
        <span id="versionNumber"></span>
    </div>

    <div class="g-sdc">
        <div class="u-tabs f-cb" id="selectEnOrBd">
            <div class="u-tab active" hs-show="JS-area-count">区域统计</div>
            <div class="u-tab" hs-show="JS-line-count">产业统计</div>
        </div>
        <div class="u-list u-list1 f-cb u-list10 JS-hs-tab-content " style="height:55%" id="JS-area-count" eb-flag="1">
        </div>
        <div class="u-list u-list1 f-cb JS-hs-tab-content u-list10" id="JS-line-count">
        </div>
    </div>
</div>
<div id="time-control">
    <div class="time-control-btns">
    </div>
    <div class="time-panel">
        <%--显示滑块当前季度--%>
        <div class="time-panel-title">
            |
        </div>
        <div class="time-panel-control">
            <div class="time-panel-progress-container" style="cursor: pointer">
                <div class="time-panel-progress-bar">
                    <%--滑块--%>
                    <span id="slide" class="time-panel-progress"></span>
                </div>
                <div class="time-panel-progress-text">
                </div>
            </div>
            <div class="time-panel-btn play" id="myPlay"></div>
        </div>
    </div>
</div>
<div id="legend" class=" BMap_noprint anchorBR">
    <div class="legend-text">企业活力热力图例：</div>
    <a href="javascript:void(0);" class="legend-item legend-1">很差
        <%--<div class="legend-item-wrapper">--%>
            <%--<div class="legend-item-tip">很差：少于20分<span class="legend-item-tip-arrow"></span></div>--%>
        <%--</div>--%>
    </a> <a href="javascript:void(0);" class="legend-item legend-2">差
    <%--<div class="legend-item-wrapper">--%>
        <%--<div class="legend-item-tip">差：20-40分<span class="legend-item-tip-arrow"></span></div>--%>
    <%--</div>--%>
</a> <a href="javascript:void(0);" class="legend-item legend-3">一般
    <%--<div class="legend-item-wrapper">--%>
        <%--<div class="legend-item-tip">一般：40-60分<span class="legend-item-tip-arrow"></span></div>--%>
    <%--</div>--%>
</a> <a href="javascript:void(0);" class="legend-item legend-4">好
    <%--<div class="legend-item-wrapper">--%>
        <%--<div class="legend-item-tip">很好：60-80分<span class="legend-item-tip-arrow"></span></div>--%>
    <%--</div>--%>
</a> <a href="javascript:void(0);" class="legend-item legend-5">很好
    <%--<div class="legend-item-wrapper">--%>
        <%--<div class="legend-item-tip">极好：大于80分<span class="legend-item-tip-arrow"></span></div>--%>
    <%--</div>--%>
</a></div>
<div class="g-mn" id="allmap"></div>
<div class="g-ft">
    <p>Copyright@2017 Youedata.All right reserved.京ICP备15024075号-1 V1.0.0.151110.BETA</p>
</div>
<jsp:include page="global/global-script.jsp"/>
<script src="${basePath}/static/heatmap/heatmap.js"></script>
<script src="${basePath}/static/heatmap/myHeatmap.js"></script>
</body>
</html>
<script>
    //鼠标移动到日历上
    $(".range_time").find("img").bind("mouseenter", function () {
        $(".u-sch-item").find("span").trigger("mouseenter")
        $($(".u-sch-item")[0]).css("overflow","visible");
    })

</script>
