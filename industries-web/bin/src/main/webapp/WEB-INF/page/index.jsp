<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成都市中小微企业运行情况分析系统 - 产业分布</title>
    <jsp:include page="global/global.jsp"/>
    <link rel="stylesheet" href="${basePath}/static/global/input.css">
    <link rel="stylesheet" href="${basePath}/static/global/index.css">
</head>
<style>
    body {
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
</style>
<script>
    var myPoints;
    var mouseenter;
</script>
<body data-identity="index">
<jsp:include page="global/header.jsp"/>
<div id="myGod">街道视图</div>
<div class="g-sd">
    <div class="g-sdc">
        <div class="u-sch">
            <div class="u-sch-1 f-cb">
                <input oninput='$("#searchBtn").trigger("click");' type="text" id="query_input"
                       placeholder="请输入企业名称或楼宇名称" name="test" class="input"
                       style="outline:none;width: 275px;-webkit-border-radius: 3px;margin-left: 20px;margin-top: 20px;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;">
                <%--<a class="clear" id="clearInput" onclick="myDeleteDeal()" style="margin-left: 245px;margin-top: 8px"></a>--%>
                <input type="submit" id="searchBtn" value="" style="outline:none;cursor: pointer;
    position: absolute;
    left: 288px;
    top: 26px;width: 20px;">
            </div>
            <div class="u-sch-2" style="margin-top: 60px">
                <div class="u-sch-group">
                    <div class="u-sch-item">
                        <span class="u-btn u-btn-c4">
                            <em data-id="" id="industries_menu">产业</em>
                            <i class="btnsel"></i>
                        </span>
                        <div class="u-sch-item-sel u-sch-item-1" id="industries_div">
                        </div>
                    </div>
                    <div class="u-sch-item">
                            <span class="u-btn u-btn-c4">
                                <em data-id="" id="business_menu">行业</em>
                                <i class="btnsel"></i>
                            </span>
                        <div style="padding: 0;left: -142px;" class="u-sch-item-sel u-sch-item-2" id="business_div">
                        </div>
                    </div>
                    <div class="u-sch-item">
                        <span class="u-btn u-btn-c4">
                            <em data-id="" id="street_menu">街道</em>
                            <i class="btnsel"></i>
                        </span>
                        <div class="u-sch-item-sel u-sch-item-4" id="street_div">
                        </div>
                    </div>
                    <div class="u-sch-item">
                        <span class="u-btn u-btn-c4">
                            <em data-id="" id="community_menu">社区</em>
                            <i class="btnsel"></i>
                        </span>
                        <div style="left: -142px;" class="u-sch-item-sel u-sch-item-2" id="community_div">
                        </div>
                    </div>

                    <%--<div class="u-sch-item">--%>
                    <%--<span class="u-btn u-btn-c4">--%>
                    <%--<em data-id="" id="category_menu">门类</em>--%>
                    <%--<i class="btnsel"></i>--%>
                    <%--</span>--%>
                    <%--<div class="u-sch-item-sel u-sch-item-3" id="category_div">--%>
                    <%--</div>--%>
                    <%--</div>--%>
                </div>
                <%--<div class="u-sch-group">--%>
                <%----%>
                <%--</div>--%>

            </div>
        </div>
    </div>
    <div class="g-sdc  g-sdc-left">
        <div class="u-rst f-cb  g-sdc-bottom">
            <div u-rst-1>搜索结果:</div>
            <div class="u-rst-1">
                <span>为您找到</span>
                <span id="enterpriseNumber" class="u-uu"></span>
                <span id="enterpriseNumber2">家企业</span>
                <span id="buildingNumber" class="u-uu"></span>
                <span id="buildingNumber2">座楼宇</span>
            </div>
            <div class="u-rst-1" style="display: none">没有找到您的数据，请确认</div>
        </div>
    </div>
    <div class="g-sdc">
        <div class="my-u-tab" id="selectEnOrBd">
            <div class="u-tab active" u-show="enterprise_body">企业列表</div>
            <div class="u-tab" u-show="build_body">楼宇列表</div>
        </div>
        <div class="u-list f-cb" id="enterprise_body" eb-flag="1" style="overflow-x: hidden">
            <div id="JS-e-pagination" class="pagination"></div>
        </div>
        <div class="u-list f-cb" id="build_body" style="display: none;overflow-x: hidden">
            <div id="JS-b-pagination" class="pagination"></div>
        </div>
        <div id="JS-show-content" class="u-ct" style="display: none;">
            <div class="u-btn JS-u-btn return-list" style="background-color: #3287AC;margin-top: 15px;">
                <span class="fa-stack fa-lg">
                  <i class="fa fa-angle-left"></i>
                </span>返回
            </div>
            <div class="u-tt"><span hs-model="name">北京雷特新技术实业公司</span></div>
            <div id="JS-show-content2">
                <div class="lab">主营业务：<span hs-model="majorBusiness">芳纶无纬布及其制品；机电产品</span></div>
                <div class="lab">地址：<span hs-model="address"></span></div>
                <div class="lab">行业：<span hs-model="tradeName">纺织业11</span></div>
                <div class="lab">从业人数：<span hs-model="employeeCount">150人</span></div>
                <div class="lab">联系电话：<span hs-model="phoneNumber">18612113167</span></div>
                <div class="lab">注册时间：<span hs-model="registerTime">2010-10-3</span></div>
                <div class="lab">注册资金：<span hs-model="registerCapital">暂无</span></div>
                <div class="lab">状态：<span hs-model="openFlag">开业</span></div>
            </div>
        </div>
        <div id="JS-building-show-content" class="u-ct u-ct-1" style="display: none;">
            <div class="u-btn JS-u-btn return-list"  style="background-color: #3287AC;margin-top: 15px;">  <span class="fa-stack fa-lg">
                  <i class="fa fa-angle-left"></i>
                </span>返回
            </div>
            <input type="hidden" id="buildId" value="" hs-model="id">
            <div class="u-tt"><span hs-model="name">旅游大厦</span></div>
            <div id="JS-building-show-content2">
                <div class="lab">楼宇内企业：<span hs-model="enterpriseSize">xxx</span></div>
                <div class="lab">地址：<span hs-model="address"></span></div>
                <div class="lab">从业人数：<span hs-model="employeeCounts">150人</span></div>
                <div id="JS-b-e-pagination" class="pagination"></div>
            </div>
        </div>

    </div>
</div>
<div class="g-mn" id="allmap"></div>
<div class="g-ft">
    <p>Copyright@2017 Youedata.All right reserved.京ICP备15024075号-1 V1.0.0.151110.BETA</p>
</div>
<jsp:include page="global/global-script.jsp"/>
<script src="${basePath}/static/index/index.js"></script>
</body>
</html>
<style>
    .u-icon-hs {
        padding-top: 5px;
        padding-left: 8px;
        font-size: large;
    }

    #myGod {
        position: absolute;
        right: 20px;
        top: 10px;
        background-color: #2E91BC;
        padding: 8px;
        color: white;
        z-index: 100;
        cursor: pointer;
        border-radius: 6px;
    }

    #myGod:hover {
        background-color: #16A9E9;
    }

    .u-item:hover{
        background-color: white!important;
        color: #2C9ACD!important;
    }


</style>
<script>
    //初始化时，地图聚焦失效
    mouseenter = false;
    //begin 鼠标移动到企业上，地图聚焦
    $("body").delegate("#enterprise_body > .u-item", "mouseenter", function () {
        if (mouseenter) {
            try {
                var focusId = $($(this).find("span")[0]).text().split("、")[0];
                var focusPoint = myPoints[(focusId - 1) % 10];
                var point = new BMap.Point(focusPoint.lng, focusPoint.lat);
                mp.centerAndZoom(point, 15);
            } catch (err) {
            }
        }
    })
    //end
    initMyGod = false;
    $("#myGod").bind("click", function () {
        if (initMyGod) {
            $("#searchBtn").trigger("click")
            initMyGod = false;

        } else {
            mp.clearOverlays();
            initializeStreet();
            initMyGod = true;
        }
    })
//    $("body").delegate("#business_menu", "change", function () {
//        console.info("aa")
//    })
</script>