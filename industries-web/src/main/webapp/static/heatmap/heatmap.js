/**
 * Created by honshe on 2016/6/6.
 */
//热力图展示
window.onload = function () {
    mp.centerAndZoom("成都",11);
    $("#JS-line-count").hide();
    document.onkeydown = function () {//回车事件
        if (event.keyCode == 13) {
            playSelectedVersion();
        }
    }
    if (sessionStorage.getItem("heatMapData") != null) {
        //findMapCenter(eval("("+sessionStorage.getItem("heatMapData")+")"));
        loadData(eval("(" + sessionStorage.getItem("heatMapData") + ")"));
    } else {
        //$.ajax({
        //    type: "get",
        //    url: basePath + "/heatmap/indexSearch.do",
        //    data: null,
        //    dataType: "json",
        //    success: function (result) {
        //        //findMapCenter(result);//确定中心点位置
        //        loadData(result);
        //        sessionStorage.setItem("heatMapData", JSON.stringify(result));
        //    },
        //    error: function () {
        //        console.log("未完成加载！");
        //    }
        //});
    }
}
//function findMapCenter(result) {//确定地图中心点位置
//    console.log("计算中心点");
//    var allVersions = result.data;
//    var points = [];//用于确定地图中心点的数组
//    var lng;
//    var lat;
//    for(var j = 0;j < allVersions[0].buildingList.length;j++) {//取第一组数据来确定地图中心点
//        lng = allVersions[0].buildingList[j].lo;
//        lat = allVersions[0].buildingList[j].la;
//        points.push({"lng": lng, "lat": lat});
//    }
//    var view = mp.getViewport(points);
//    mp.setViewport(view);
//    var mapZoom = view.zoom;
//    var centerPoint = view.center;
//    console.log(view.enableAnimation);
//    console.log(centerPoint);
//    mp.centerAndZoom(centerPoint,mapZoom);//根据坐标自动初始化位子
//    console.log("结束计算");
//}
/*
 加载时间选项下拉框以及进度条数据
 */
function loadData(result) {
    versions = result.data;
    versionLength = versions.length - 1;//轮播热力图的进度条分栏数
    //i = versions.length - 1;
    //loadMap();//默认显示最近版本的热力图信息
    var $opation1;
    var $opation2;
    endVersion = versions[versions.length - 1].version;
    startVersion = versions[0].version;
    firstVersion = versions[0].version;
    if (versions.length >= 5) {//默认显示最近5年
        versionLength = 4;
        startVersion = endVersion - 4;
    }
    for (j = 0; j < versions.length; j++) {
        var version = versions[j].version + "";//转为字符串
        $option1 = $("<a href = 'javascript:void(0)' data-id = " + "'" + (j + 1) + "'" + ">" + version + "</a>");
        $option2 = $("<a href = 'javascript:void(0)' data-id = " + "'" + (j + 1) + "'" + ">" + version + "</a>");
        $option1.data("startFlag", true);
        $option2.data("startFlag", false);
        if (j == versions.length - 1) {//版本（时间）下拉框
            //$("em:last").html(version);
        }
        $("#startTime").append($option1);//加载时间选项
        $("#endTime").append($option2);//加载时间选项
    }
    if (versions.length >= 5) { //版本（时间）下拉框
        $("em:first").html(versions[versions.length - 5].version);
    } else {
        $("em:first").html(versions[0].version);
    }
    $(".u-rst.f-cb").find("a").css("width", "75px");//下拉框宽度
    $("a").bind("click", loadVersion);
    beginTimeControl($("em:last").html());
    endTimeControl($("em:first").html());
    play();
    $("em:first").html("2017");
}
/*
 加载版本（年份）选项信息
 */
function loadVersion() {
    var selectedVersion = $(this).html();
    $(this).parent().prev().find("em").html(selectedVersion);
    $(this).parent().hide();
    var startFlag = $(this).data("startFlag");
    //if (startFlag) {
    //    endTimeControl(selectedVersion);
    //} else {
    //    beginTimeControl(selectedVersion);
    //}
}
function beginTimeControl(selectedVersion) {//根据选择的endTime控制startTime选项
    var $a = $("#startTime").find("a");
    var $em = $("#endTime").prev().find("em");
    if (parseInt($("#startTime").prev().find("em").html()) <= parseInt(selectedVersion)) {

    } else if ((parseInt($em.html()) - 1) < parseInt(versions[0].version)) {
        $("#startTime").prev().find("em").html($em.html());
    } else {
        $("#startTime").prev().find("em").html((parseInt($em.html())) - 1);
    }
    var value;
    $.each($a, function () {
        value = parseInt($(this).html());
        if (parseInt(selectedVersion) < value) {
            $(this).hide();
        } else {
            $(this).show();
        }
    })
}

function endTimeControl(selectedVersion) {//根据选择的startTime控制endTime选项
    var $a = $("#endTime").find("a");
    var $em = $("#startTime").prev().find("em");
    if (parseInt($("#endTime").prev().find("em").html()) >= parseInt(selectedVersion)) {

    } else if ((parseInt($em.html()) + 1) > parseInt(versions[versions.length - 1].version)) {
        $("#endTime").prev().find("em").html($em.html());
    } else {
        $("#endTime").prev().find("em").html((parseInt($em.html())) + 1);
    }
    var value;
    $.each($a, function () {
        value = parseInt($(this).html());
        if (parseInt(selectedVersion) > value) {
            $(this).hide();
        } else {
            $(this).show();
        }
    })
}

/*
 加载热力图
 区域统计，行业统计以及热力图数据
 */
point = [];
function loadMap() {
    var oldDiv = $(".heatmap-canvas").parent();
    point.length = 0;//全局数组清空
    //var buildings = versions[i].buildingList;
    var points = versions[i].point;
    var streets = versions[i].streetList;
    var categories = versions[i].categorieList;
    var previousVersion = parseInt($("#versionNumber").html().substring(0, 4));
    if (versions[i].version == previousVersion) {//此次点击版本与上一次点击的版本相同时处理
        return;
    }
    $("#JS-area-count").children().remove();
    $("#JS-line-count").children().remove();
    $("#versionNumber").html(versions[i].version + "年");
    var lng;
    var lat;
    var count;
    if(points!=null){
        for (var j = 0; j < points.length; j++) {
            if(points[j] == null) {
                continue;
            }
            lng = points[j].lo;
            lat = points[j].la;
            count = points[j].co;
            point.push({"lng": lng, "lat": lat, "count": count});
        }
    }

    var newHeatmapOverlay = new BMapLib.HeatmapOverlay({"radius": 40});
    setHeatmapData(newHeatmapOverlay, point);//为热力图加载数据
    mp.addOverlay(newHeatmapOverlay);
    var newDiv = $(".heatmap-canvas:last").parent();
    var showFlag = true;
    var index = 0;
    $(newDiv).css("position", "absolute");
    $(oldDiv).css("position", "absolute");
    $(newDiv).css("opacity", "0.1");
    $(oldDiv).fadeTo(1000, 0.1);//热力图淡出
    $(newDiv).fadeTo(1000, 0.9);//热力图淡入
    setTimeout(function () {
        $(oldDiv).remove();
    }, 1000)
    flag = true;
    var enterpriseNumber = 0;
    if(streets!=null){
        for (var j = 0; j < streets.length; j++) {
            var streetName = streets[j].name;
            var enterpriseCounts = streets[j].enterpriseCounts;
            var $div = $("<div class='u-item1'>" +
                "<span>" + (j + 1) + "、" + "</span>" +
                "<span>" + streetName + "</span>" +
                "<span>" + enterpriseCounts + "家" + "</span>" +
                "</div>");
            if (j + 1 < 10) {
                $div.css("padding-left", 22 + "px");//前九个街道向右平移，使上下街道文字开头对齐
            }
            $("#JS-area-count").append($div);
            var $span = $("#JS-area-count span:last");
            $span.css("opacity", 0.1);
            dataIn($span);
            enterpriseNumber += enterpriseCounts;
        }
    }else{
        enterpriseNumber=0;
    }

    $("#enterpriseNumber").html(enterpriseNumber);//加载企业总数量
    var categoryName;
    var enterpriseCounts;
    var $div;
    if(categories!=null){
    for (var j = 0; j < categories.length; j++) {
        categoryName = categories[j].name;
        enterpriseCounts = categories[j].enterpriseCounts;
        $div = $("<div class='u-item1' style='cursor: pointer' onclick='showCategory("+(j + 1)+")'>" +
            "<span>" + (j + 1) + "、" + "</span>" +
            "<span>" + categoryName + "</span>" +
            "<span>" + enterpriseCounts + "家" + "</span>" +
            "</div>");
        $("#JS-line-count").append($div);
        var $span = $("#JS-line-count span:last");
        $span.css("opacity", 0.1);
        dataIn($span);
    }
    }
    $(".u-item1").bind("mouseover", function () {
        $(this).find("span").css("color", "#11B8FF");
    });
    $(".u-item1").bind("mouseout", function () {
        $(this).find("span").css("color", "#035F86");
    });
    $("#legend").click(function(){
        $("#legend").show();
    });
}

/*
 设置热力图数据
 */
function setHeatmapData(newHeatmapOverlay, point) {
    setTimeout(function () {
        newHeatmapOverlay.setDataSet({data: point, max: 100});
    }, 0)
}
var firstInFlag = true;//第一次进入标示
var progressId = 0;//进度条计时器id
var minusVersion = 0;
var lastWaitId = 0;//进度条跑完一遍之后到最后位置时，停顿的定时器id
//播放热力图
function playHeatMap() {
    $("#play").removeClass("play");
    //if (i - minusVersion == versionLength) {//最后一组数据
    //    number = 0;
    //    i = minusVersion;
    //}
    if (firstInFlag) {
        flag = true;//首次进入播放区间标示
        i = 0;
        number = 0;
        //loadMap();
        firstInFlag = false;
    }
    if (number == 0) {
        if (i == minusVersion) {
            var previousVersion = parseInt($("#versionNumber").html().substring(0, 4));
            if (versions[i].version == previousVersion) {//此次点击版本与上一次点击的版本相同时处理
                return;
            }
        }
        loadMap();
        firstInFlag = false;
    }
    window.clearInterval(progressId);
    var baseNumber = 252 / (versionLength + 1);
    var increaseNumber = 0.28;//进度条每次前进长度
    var time = versionLength * 7;//循环单次时间
    if (versionLength <= 2) {//三组数据时，播放速度放慢
        time = versionLength * 10;
    }
    $(".time-panel-progress").css("left", number + "px");
    progressId = window.setInterval(function () {//进度条定时器
        number += increaseNumber;
        //if ((number % baseNumber >= (baseNumber - 1))//轮播时，切换热力图条件判断
        //    && (number % baseNumber <= (baseNumber - 1 + increaseNumber)) && flag) {
        if ((number % baseNumber) >= (baseNumber / 2 - 8 - increaseNumber) && (number % baseNumber) <= (baseNumber / 2 - 8 ) && flag) {
            flag = false;
            i++;
            if (i < versions.length) {
                loadMap();
            }
        }
        $(".time-panel-progress").css("left", number + "px");
        if (number >= 246) {
            //if (++i == versionLength + minusVersion) {
            //    flag = false;
            //    loadMap();
            //}
            //window.clearInterval(progressId);
            number = 0;
            if (minusVersion == 0) {
                firstInFlag = true;
            }
            i = minusVersion - 1;
        }
    }, time);//控制播放速度
    $("#play").one("click", pauseHeatMap);
}
//改变权重值淡出热力图(未使用该方法)
var index = 0;
function fadeOut() {
    index++;
    for (var j = 0; j < point.length; j++) {
        point[j].count *= 0.8;
    }
    newHeatmapOverlay.setDataSet({data: point, max: 100});
    if (index >= 8) {
        clearTimeout(fadeOutId);
        point.length = 0;
        newHeatmapOverlay.setDataSet({data: point, max: 100});
        index = 0;
        return;
    }
    fadeOutId = setTimeout(fadeOut, 1);
}

//暂停热力图播放
function pauseHeatMap() {
    clearTimeout(lastWaitId);
    clearInterval(progressId);//暂停进度条
    $("#play").addClass("play");
    $("#play").one("click", playHeatMap);
}

//企业数量淡入
function dataIn($span) {
    var j = 0.1;
    var dataInId = setInterval(function () {
        j += 0.1;
        $span.css("opacity", j);
        if (j >= 1) {
            clearInterval(dataInId);
        }
    }, 50)
}

var dataOutId;
//文字淡出
function dataOut($span) {
    var j = 1;
    dataOutId = setInterval(function () {
        j -= 0.1;
        $span.css("opacity", j);
        if (j <= 0.1) {
            $span.html("");
            clearInterval(dataOutId);
        }
    }, 80)
}

//进度条版本（年份）数字点击事件
function versionShow() {
    i = $(this).data("version");
    loadMap();
    //if (1 <= versionLength && versionLength <= 4) {
    //    number = (92.5 / versionLength) * (i - minusVersion) + 0.5 * (i - minusVersion);
    //} else if (versionLength == 5) {//对选择了6个年份进行播放的进度条进行调整
    //    number = (92.5 / versionLength) * (i - minusVersion) + 0.07 * (i - minusVersion);
    //} else {
    //    number = (92.5 / versionLength) * (i - minusVersion) - 0.07 * (i - minusVersion);
    //}
    //if (i - minusVersion == 1) {//轮播的第二个年份
    //    if (versionLength >= 4) {
    //        number = (92.5 / versionLength) * (i - minusVersion) - 0.8 * (i - minusVersion);
    //    } else if (versionLength == 2) {//选择三个年份轮播
    //        number = (92.5 / versionLength) * (i - minusVersion) + 0.5 * (i - minusVersion);
    //    } else {
    //        number = (92.5 / versionLength) * (i - minusVersion) - 0.7 * (i - minusVersion);
    //    }
    //}
    //if (number >= 92.5 || $(this).data("lastFlag")) {
    //    if (versionLength <= 4) {
    //        number = 94.5;
    //    } else {
    //        number = 92.5;
    //    }
    //}
    //if (i - minusVersion == 0) {//点击第一个版本，进度条默认返回-1.5位置
    //    number = -1.5;
    //}
    var perLength = 252 / (versionLength + 1);
    if (i - minusVersion - 1 >= 0) {
        number = (i - minusVersion + 0.5) * perLength - 8;
    } else {
        number = 0.5 * perLength - 8;
    }
    $(".time-panel-progress").css("left", number + "px");
    firstInFlag = false;
    flag = true;
    pauseHeatMap();
}

var reminderId;
//播放所选择的版本热力图
function playSelectedVersion() {
    startVersion = $("em:first").html();//选择的第一个版本
    firstVersion = versions[0].version;//所有数据中第一个版本
    endVersion = $("em:last").html();//选择的最后一个版本
    console.info(startVersion)
    console.info(endVersion)
    versionLength = endVersion - startVersion;//轮播条分栏数
    if (endVersion - startVersion < 0) {//后面版本小于前面版本处理
        clearTimeout(reminderId);
        $("#reminder").html("日期选择错误!");
        $("#reminder").css("color", "	red");
        $("#reminder").css("opacity", "1");
        $("#reminder").css("font-size", 2 + "px");
        $("#reminder").fadeIn(200);
        reminderId = setTimeout(function () {
            $("#reminder").fadeOut(1000);
        }, 3000);
        return;
    }
    if (versionLength == 0) {
        $(".time-panel").hide(1000);//隐藏轮播框
    } else {
        $(".time-panel").show(1000);//显示轮播框
    }
    clearInterval(progressId);
    play();
}

function play() {
    $("#play").addClass("play");
    $("#play").one("click", playHeatMap);
    minusVersion = startVersion - firstVersion;//选择的第一个版本与所有数据中第一个版本的差
    firstVersion = (firstVersion + "").substring(2);
    firstVersion = parseInt(firstVersion);
    versionLength = endVersion - startVersion;
    var $div;
    var version;
    var $versionDiv;
    $(".time-panel-progress-text").empty();
    $(".time-panel-progress-tick").remove();
    for (var j = 0; j < versionLength + 1; j++) {
        version = firstVersion + j + minusVersion;
        if (version < 10) {
            var newVersion = "0" + version;
            $div = $("<div class = 'time-panel-progress-tick'><div class = 'version'><span>" + newVersion +
                "</span></div></div>");
        } else {
            $div = $("<div class = 'time-panel-progress-tick'><div class = 'version'><span>" + version +
                "</span></div></div>");
        }
        $div.css("float", "left");
        $versionDiv = $div.find(".version");
        $versionDiv.data("version", j + minusVersion);
        if (j == versionLength + 1) {
            if (versionLength == 0) {//只选择一个数据的时候
                $div.css("width", 252 / (versions.length + 1) + "px");
            } else {
                $div.css("width", 252 / (versions.length + 1) + "px");
            }
        } else if (j >= 1) {
            $div.css("width", 252 / (versionLength + 1) + "px");
        } else {
            $div.css("width", (252 / (versionLength + 1)) + "px");
        }
        $(".time-panel-progress-text").append($div);
        if (j == versionLength) {
            $versionDiv.data("lastFlag", true);
        } else {
            $versionDiv.data("lastFlag", false);
        }
    }
    $(".version").css({"cursor": "pointer"});
    $(".version").bind("click", versionShow);
    $(".time-panel-progress-tick").css("background-position", 50 + "% " + 0);
    $(".time-panel-title").html(startVersion + " - " + endVersion);
    var perLength = 252 / (versionLength + 1);
    number = 0.5 * perLength - 8;
    $(".time-panel-progress").css("left", number + "px");
    i = minusVersion;
    firstInFlag = false;
    loadMap();
}