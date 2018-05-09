/**
 * Created by cdyoue on 2017/3/27.
 */
var myPoint;
//点过滤
function filterCategory() {
    var category = arguments[0];
    var categoryName = arguments[1];

    pushFilterCategory=category;

    //是否过滤行业
    var filterCategoryName = false;
    if (categoryName != undefined) {
        filterCategoryName = true;
        pushFilterCategoryName=categoryName;
    }else{
        pushFilterCategoryName=undefined;
    }
    var points = myPoint;
    point.length = 0;
    var oldDiv = $(".heatmap-canvas").parent();
    var lng;
    var lat;
    var count;
    for (var j = 0; j < points.length; j++) {
        if (points[j] == null || points[j].category != category) {
            continue;
        }
        if (filterCategoryName) {
            if (points[j].categoryName != categoryName) {
                continue;
            }
        }
        lng = points[j].lo;
        lat = points[j].la;
        count = points[j].co;
        var temp=parseInt(count/20);
        if(temp==0){
            count=70
        }else if(temp==1){
            count=94
        }else if(temp==2){
            count=96
        }else if(temp==3){
            count=98
        }else{
            count=100
        }
        point.push({"lng": lng, "lat": lat, "count": count});
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
    $(oldDiv).remove();
}
//时间加载 现在时间-2014年
var myYear = new Date().getFullYear();
function year() {
    $("#selectYear").text(myYear);
    for (var myI = 0; myI < 4; myI++) {
        $("#startTime1").append('<a>' + (myYear - myI) + '</a>');
    }
}
year();
//加载数据到地图
var pushFilterCategory = 1;
var pushFilterCategoryName;
var myCategory = {c1: {number: 0, list: []}, c2: {number: 0, list: []}, c3: {number: 0, list: []}};
function pushData(points) {
    //console.info(points)
    point.length = 0;
    var oldDiv = $(".heatmap-canvas").parent();
    var lng;
    var lat;
    var count;
    myCategory = {c1: {number: 0, list: []}, c2: {number: 0, list: []}, c3: {number: 0, list: []}};
    for (var j = 0; j < points.length; j++) {
        if (points[j] == null) {
            continue;
        }
        //产业计数
        if (points[j].category == 1) {
            var ok = true;
            myCategory.c1.number++;
            //行业计数
            for (var i = 0; i < myCategory.c1.list.length; i++) {
                if (points[j].categoryName == myCategory.c1.list[i].name) {
                    myCategory.c1.list[i].number++;
                    ok = false;
                    break;
                }
            }
            if (ok) {
                myCategory.c1.list.push({name: points[j].categoryName, number: 1});
            }
        } else if (points[j].category == 2) {
            var ok = true;
            myCategory.c2.number++;
            for (var i = 0; i < myCategory.c2.list.length; i++) {
                if (points[j].categoryName == myCategory.c2.list[i].name) {
                    myCategory.c2.list[i].number++;
                    ok = false;
                    break;
                }
            }
            if (ok) {
                myCategory.c2.list.push({name: points[j].categoryName, number: 1});
            }
        } else {
            var ok = true;
            myCategory.c3.number++;
            for (var i = 0; i < myCategory.c3.list.length; i++) {
                if (points[j].categoryName == myCategory.c3.list[i].name) {
                    myCategory.c3.list[i].number++;
                    ok = false;
                    break;
                }
            }
            if (ok) {
                myCategory.c3.list.push({name: points[j].categoryName, number: 1});
            }
        }
        if (pushFilterCategory != undefined) {
            if (points[j].category != pushFilterCategory) {
                continue;
            }
        }
        if (pushFilterCategoryName != undefined) {
            if (points[j].categoryName != pushFilterCategoryName) {
                continue;
            }
        }

        lng = points[j].lo;
        lat = points[j].la;
        count = points[j].co;
        var temp=parseInt(count/20);
        if(temp==0){
            count=70
        }else if(temp==1){
            count=94
        }else if(temp==2){
            count=96
        }else if(temp==3){
            count=98
        }else{
            count=100
        }
        point.push({"lng": lng, "lat": lat, "count": count});
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
}

function myLoadData(year, quarter) {
    $.ajax({
        url: basePath + "/heatmap/indexSearch1.do",
        type: "POST",
        data: {year: year, quarter: quarter},
        success: function (result) {
            //console.info(result)
            $("#JS-line-count").empty();
            $("#JS-area-count").empty();
            //console.info("数据加载ok")
            if (result.data.point != null) {
                $("#enterpriseNumber").text(result.data.point.length)
                $("#versionNumber").text("")
                myPoint = result.data.point;
                pushData(myPoint);
                var streets = result.data.streetList;
                //加载产业
                $div = $("<div class='u-item1 my-heat-head' id='head1' onclick='filterCategory(1)'>" +
                    "<span><img id='img1' style='width: 12px' src='"+basePath+"/static/images/xiala.png'/></span>" +
                    "<span>" + "第一产业" + "</span>" +
                    "<span>" + myCategory.c1.number + "家" + "</span>" +
                    "</div>");
                $("#JS-line-count").append($div);
                for (var i in myCategory.c1.list) {
                    $div = $("<div id='"+myCategory.c1.list[i].name+"' class='list1 u-item1 my-heat-list my-heat-list-1' onclick='filterCategory(1," + "\"" + myCategory.c1.list[i].name + "\"" + ")'>" +
                        "<span></span>" +
                        "<span>" + myCategory.c1.list[i].name + "</span>" +
                        "<span>" + myCategory.c1.list[i].number + "家" + "</span>" +
                        "</div>");
                    $("#JS-line-count").append($div);
                }
                $div = $("<div class='u-item1 my-heat-head' id='head2' onclick='filterCategory(2)'>" +
                    "<span><img id='img2' style='width: 12px' src='"+basePath+"/static/images/xiala.png'/></span>" +
                    "<span>" + "第二产业" + "</span>" +
                    "<span>" + myCategory.c2.number + "家" + "</span>" +
                    "</div>");
                $("#JS-line-count").append($div);
                for (var i in myCategory.c2.list) {
                    $div = $("<div id='"+myCategory.c2.list[i].name+"' class='list2 u-item1 my-heat-list my-heat-list-2' onclick='filterCategory(2," + "\"" + myCategory.c2.list[i].name + "\"" + ")'>" +
                        "<span></span>" +
                        "<span>" + myCategory.c2.list[i].name + "</span>" +
                        "<span>" + myCategory.c3.list[i].number + "家" + "</span>" +
                        "</div>");
                    $("#JS-line-count").append($div);
                }
                $div = $("<div class='u-item1 my-heat-head' id='head3' onclick='filterCategory(3)'>" +
                    "<span><img id='img3' style='width: 12px' src='"+basePath+"/static/images/xiala.png'/></span>" +
                    "<span>" + "第三产业" + "</span>" +
                    "<span>" + myCategory.c3.number + "家" + "</span>" +
                    "</div>");
                $("#JS-line-count").append($div);
                for (var i in myCategory.c3.list) {
                    $div = $("<div id='"+myCategory.c3.list[i].name+"' class='list3 u-item1 my-heat-list my-heat-list-3' onclick='filterCategory(3," + "\"" + myCategory.c3.list[i].name + "\"" + ")'>" +
                        "<span></span>" +
                        "<span>" + myCategory.c3.list[i].name + "</span>" +
                        "<span>" + myCategory.c3.list[i].number + "家" + "</span>" +
                        "</div>");
                    $("#JS-line-count").append($div);
                }
                var $span = $("#JS-line-count span:last");
                $span.css("opacity", 0.1);
                dataIn($span);

                //加载街道
                var enterpriseNumber = 0;
                if (streets != null) {
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
                } else {
                    enterpriseNumber = 0;
                }

                //产业选项更改默认样式
                if(pushFilterCategory==1){
                    $(".list1").toggleClass("my-heat-list-1")
                    $("#img1").attr("src",basePath+"/static/images/shangla.png")
                }
                if(pushFilterCategory==2){
                    $(".list2").toggleClass("my-heat-list-2")
                    $("#img2").attr("src",basePath+"/static/images/shangla.png")

                }
                if(pushFilterCategory==3){
                    $(".list3").toggleClass("my-heat-list-3")
                    $("#img3").attr("src",basePath+"/static/images/shangla.png")
                }
                if(pushFilterCategoryName!=undefined){
                    $(("#"+pushFilterCategoryName)).addClass("active")
                }else{
                    if(pushFilterCategory==1){
                        $("#head1").addClass("active")
                    }
                    if(pushFilterCategory==2){
                        $("#head2").addClass("active")
                    }
                    if(pushFilterCategory==3){
                        $("#head3").addClass("active")
                    }


                }
            } else {
                //console.info("当季度没有数据")
                $("#enterpriseNumber").text(0)
                pushData([]);
            }
        },
        error: function () {
            console.log("未完成加载！");
        }
    });
}
//myYear=2015;
//myLoadData(myYear, 1);
function myPlaySelectedVersion() {
    var selectYear = $("#selectYear").text();
    var selectQuarter = $("#selectQuarter").text();
    if (selectQuarter == "第一季度") selectQuarter = 1;
    if (selectQuarter == "第二季度") selectQuarter = 2;
    if (selectQuarter == "第三季度") selectQuarter = 3;
    if (selectQuarter == "第四季度") selectQuarter = 4;
    myLoadData(selectYear, selectQuarter);
}
//时间点击事件
$(".u-sch-item a").click(function () {
    $(".u-sch-time").css("display","none");
    if ($(this).text() < 2099) {
        $("#selectYear").text($(this).text())
    } else {
        $("#selectQuarter").text($(this).text())
    }
    myPlaySelectedVersion();
});

var myInterval;
$("#myPlay").click(function () {
    $(this).toggleClass("play")

    //滑块静止
    if (myInterval != undefined) {
        window.clearInterval(myInterval);
        myInterval = undefined;
    } else {
        myInterval = window.setInterval('slideMove()', 100)
    }
});
function slideMove() {
    if ($("#slide").css("left").split("p")[0] == 240) {
        $("#slide").css("left", "0px");
    }
    $("#slide").css("left", parseInt($("#slide").css("left").split("p")[0]) + 1 + "px");
}
//点击移动滑块
$(".time-panel-progress-container").click(function (event) {
    $("#slide").css("left", event.pageX - ($(".time-panel").offset().left + 21) + "px");
})

//滑块加载年份
var myYear = new Date().getFullYear();
for (var i = 0; i < 5; i++) {
    $(".time-panel-progress-container").append("<div class='scale' style='left:" + 60 * i + "px '>" + (myYear - 3 + i) + "</div>");
    $(".time-panel-progress-container").append("<div class='scaleDown' style='left:" + (19 + 60 * i) + "px '>|</div>");
}
var timeInterval = parseInt($("#slide").css("left").split("p")[0]) / 15;
function loadDateInterval() {
    if (Math.abs(timeInterval - parseInt($("#slide").css("left").split("p")[0]) / 15) >= 1) {
        timeInterval = parseInt($("#slide").css("left").split("p")[0]) / 15;
        var year = parseInt(parseInt(timeInterval) / 4) + 2014;
        var quarter = parseInt(timeInterval) % 4 + 1;
        //console.info(year + "-" + quarter)
        $(".time-panel-title").text(year + " 年 - 第 " + quarter + " 季度")
        myLoadData(year, quarter);
    }
}
window.setInterval("loadDateInterval()", 100)
//初始化当前时间
function myInit() {
    //var myYear = new Date().getFullYear();
    var myYear = new Date().getFullYear()-1;
    var myMonth = new Date().getMonth() + 1;
    var myQuarter = parseInt(myMonth / 4) + 1;
    $(".time-panel-title").text(myYear + " 年 - 第 " + myQuarter + " 季度")
    $("#selectYear").text(myYear);
    //var slideLeft = 180 + (myQuarter - 1) * 20;
    var slideLeft = 120 + (myQuarter - 1) * 20;
    if (myQuarter == 1) myQuarter = "一";
    if (myQuarter == 2) myQuarter = "二";
    if (myQuarter == 3) myQuarter = "三";
    if (myQuarter == 4) myQuarter = "四";
    $("#selectQuarter").text("第" + myQuarter + "季度");
    $("#slide").css("left", slideLeft + "px");
}
myInit()

$("body").delegate("#head1","click",function () {
    $(".my-heat-list").removeClass("active")
    $(".list1").toggleClass("my-heat-list-1")
    if($("#img1").attr("src").indexOf("shangla")!=-1){
        $("#img1").attr("src",basePath+"/static/images/xiala.png")
    }else{
        $("#img1").attr("src",basePath+"/static/images/shangla.png")
    }
    pushFilterCategory=1;
    pushFilterCategoryName=undefined;
})
$("body").delegate("#head2","click",function () {
    $(".my-heat-list").removeClass("active")
    $(".list2").toggleClass("my-heat-list-2")
    if($("#img2").attr("src").indexOf("shangla")!=-1){
        $("#img2").attr("src",basePath+"/static/images/xiala.png")

    }else{
        $("#img2").attr("src",basePath+"/static/images/shangla.png")

    }
    pushFilterCategory=2;
    pushFilterCategoryName=undefined;
})
$("body").delegate("#head3","click",function () {
    $(".my-heat-list").removeClass("active")
    $(".list3").toggleClass("my-heat-list-3")
    if($("#img3").attr("src").indexOf("shangla")!=-1){
        $("#img3").attr("src",basePath+"/static/images/xiala.png")

    }else{
        $("#img3").attr("src",basePath+"/static/images/shangla.png")

    }
    pushFilterCategory=3;
    pushFilterCategoryName=undefined;
})
$("body").delegate(".u-item1","click",function () {
    $(".u-item1").removeClass("active")
    $(this).addClass("active")
})