<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成都市中小微企业运行情况分析系统 - 运行分析</title>
    <jsp:include page="global/global.jsp"/>
    <link rel="stylesheet" href="${basePath}/static/global/datetimepicker/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="${basePath}/static/global/datetimepicker/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/static/global/style.css">
    <link rel="stylesheet" href="${basePath}/static/global/change.css">
</head>
<body data-identity="change">

<jsp:include page="global/header.jsp"/>
<div class="change-title">
    <div id="a"><img src="${basePath}/static/global/images/changeBule.png">成都市中小微企业运行指数分析</div>
</div>
<div id="main" class="container-fluid">
    <div class="row col-sm-12">
        <div class="bs-example bs-example-tabs col-sm-12" data-example-id="togglable-tabs">
            <div class="tabbable">
                <ul class="nav nav-tabs" id="myTab">
                    <li id="one-industry" class="dropdown active">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span style="color: #035F86">第一产业</span><span
                                class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-info" id="one-contents"></ul>
                    </li>
                    <li id="second-industry" class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span style="color: #035F86">第二产业</span><span
                                class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-info" id="two-contents"></ul>
                    </li>
                    <li id="three-industry" class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span style="color: #035F86">第三产业</span><span
                                class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-info" id="three-contents"></ul>
                    </li>
                </ul>
            </div>
            <div id="mainContent">
                <div id="content" class="col-xs-12" style="height: 600px"></div>
                <div>
                    <p><span style="font-weight:bold">环境支撑：</span>环境支撑指标是衡量一个中小微企业环境支撑体系的公平竞争、健全和有利程度。
                        <span class="more-describe">更多...</span>
                        <span class="hidden">从企业的角度来讲，环境支撑包括实体环境和商业环境。企业所处地理位置、办公配套等实体环境能一定程度上对企业创新转型有积极作用，
                        也能间接反映该企业的经济能力。商业环境由园区优惠政策、税收减免、园区内商业竞争等指标构成。区域政府对本地区产业园区的扶持力度，
                        包括税收减免和房租减免等优惠政策，都反映了区域政府对区域中小微企业经营发展的推动作用。</span></p>
                    <p><span style="font-weight:bold">企业素质：</span>企业素质指的是企业各要素的质量及其相互结合的本质特征，它是决定企业生产经营活动所必须具备的基本要素的有
                        机结合所产生的整体功能。
                        <span class="more-describe">更多...</span>
                        <span class="hidden">企业素质是一个质的概念而不是量的概念，因此除了企业规模，还要注重其内在质量在分析企业素质时，分析企业基础信息、融资能力、管理层素
                            质及企业人才素质各个要素之间的内在联系和相互整合。</span></p>
                    <p><span style="font-weight:bold">技术创新能力：</span>技术创新的经济效益往往由企业作为主体完成，技术创新能力指标反映了企业对于创新研发的投入力度、创新产
                        出的能力、以及对社会带来的经济价值贡献等。
                        <span class="more-describe">更多...</span>
                        <span class="hidden">科研投入包括R&D经费和科研人力投入，企业想要获得核心知识和能力，就必须加大对研发的投入，研发可以让企业获得新技术、新知识和新能力。</span></p>
                    <p><span style="font-weight:bold">债偿能力：</span>企业偿债能力是反映企业财务状况和经营能力的重要标志。
                        <span class="more-describe">更多...</span>
                        <span class="hidden">偿债能力是企业偿还到期债务的承受能力或保证程度，包括偿还短期债务和长期债务的能力。</span></p>
                    <p><span style="font-weight:bold">扩张能力：</span>企业扩张能力主要由盈利能力和成长能力共同体现。
                        <span class="more-describe">更多...</span>
                        <span class="hidden">盈利能力就是企业资金增值的能力，通常表现为企业收益数额的大小与水平的高低。成长能力是指企业未来发展趋势与发展速度，
                            包括企业规模的扩大，利润和所有者权益的增加，反映了企业未来的发展前景。</span></p>
                    <p><span style="font-weight:bold">预期盈利：</span>预期盈利指如果没有意外事件发生时根据已知信息所预测能得到的盈利。
                        <span class="more-describe">更多...</span>
                        <span class="hidden">由资产收益预测值和业务利润预测值共同影响。能反映企业发展趋势及可能的经营状况。</span></p>
                    <p><span style="font-weight:bold">预期业务拓展：</span>预期业务拓展指如果没有意外事件发生时根据已知信息所预测能得到的业务拓展情况。
                        <span class="more-describe">更多...</span>
                        <span class="hidden">由企业预期成长和预期人力资源获得。预期成长是指企业未来发展趋势与发展速度，包括企业规模的扩大，利润和所有者权益的增加，
                            预期人力资源能反映企业的发展规划。</span></p>
                </div>
            </div>

        </div>
    </div>
</div>
<div class="g-ft">
    <p>Copyright@2017 Youedata.All right reserved.京ICP备15024075号-1 V1.0.0.151110.BETA</p>
</div>
</body>
</html>
<script src="${basePath}/third-party/jquery.min.js"></script>
<script src="${basePath}/static/bootstrap-3.3.5/js/bootstrap.min.js"></script>
<script src="${basePath}/static/change.js"></script>
<script src="${basePath}/static/echarts/echarts.js"></script>
<script src="${basePath}/static/chart.js"></script>
<script>
    $("li[hs-active='change']").addClass("active");
</script>