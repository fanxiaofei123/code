/**
 * Created by honshe on 2016/5/30.
 */
var circleOverply = null;//地图企业覆盖物
var zIndexNumber = null;
var firstClick;
var allOverMap;
var centerPoint;
var zoomScale;
var enterpPoint;
var blueCircleElement = null;
var blueCircleElement2 = null;
var infoChangePaginationFlag = 0;//0表示 需要取消变更和确认变更按钮 1表示只需要取消变更按钮 2表示只需要确认变更按钮
var addressOrCanceFlag = 1;
var changeDateFlag;
//街道点击事件
function streetClick(data) {
    firstClick = true;
    //给街道下拉框赋值
    $("#street_menu").html(data.name);
    $("#street_menu").attr("data-id", data.id);
    mp.clearOverlays();
    //初始化社区下拉框
    renderCommunity();
    //sign
    init();

}
//社区点击事件
function communityClick(data) {
    mp.clearOverlays();
    //社区下拉框赋值
    $("#community_menu").html(data.name);
    $("#community_menu").attr("data-id", data.id);

    var identity = $("body").data("identity");
    var flag = $("#enterprise_body").attr("eb-flag");

    init();
}
//企业点击事件
function enterpriseClick(data) {
    if (data.openFlag == null || data.openFlag == "") {
        data.openFlag = "暂无";
    }
    if(data.openFlag==1){
        data.openFlag="营业"
    }else if(data.openFlag==0){
        data.openFlag="停业"
    }
    if (data.registerCapital == null || data.registerCapital == "") {
        data.registerCapital = "暂无";
    }
    var identity = $("body").data("identity");
    if (identity == "index") {
        if (data.employeeCount == null) {
            data.employeeCount = "暂无数据";
        }
        //数据绑定
        HSUtil.dataBind("#JS-show-content", data);
        if (data.sourceLogs != null) {
            HSUtil.dataBind("#JS-show-content2", data.sourceLogs[0]);
        }

        if (data.trade != null) {
            HSUtil.dataBind("#JS-show-content2", data.trade);
        }
    } else {
        var ebFlag = $("#enterprise_body").attr("eb-flag");
        if (ebFlag == 1) {
            infoChangePaginationFlag = 0;
        }
        if (data.employeeCount != "暂无") {
            var ren = data.employeeCount.substr(data.employeeCount.length - 1, data.employeeCount.length);
            if (ren != "人") {
                data.employeeCount += "人";
            }
        }

        infoChangePageEnterpriseDetail(data);
    }


    if (data.id == null) {
        data.id = 0;
    }


    var array = window.getElementByAttr("div", "hs-flag", data.id);
    if (array.length != 0) {
        blueCircleElement = array[0];
        array[0].style.backgroundImage = "url(" + basePath + "/static/global/images/hoverIcon.png)";
        array[0].style.overflow = "visible";
    }

    if (data.streetId != null) {
        var array2 = window.getElementByAttr("div", "hs-flag", data.streetId);
        if (array2.length != 0) {
            blueCircleElement2 = array2[0];
        }
    }
    $("#JS-show-content").show();
}

//信息变更页面企业点击详情
function infoChangePageEnterpriseDetail(data) {
    var points = [];
    var str = "";
    var queryCondition = {"id": data.id};
    if (infoChangePaginationFlag == 1) {
        queryCondition.isModifyLog = 1;
    }
    var getList = $.ajax({
        url: basePath + "/enterPrise/selectChangesEnterprise.do",
        data: queryCondition,
        async: false,
        type: "get",
        success: function (rsl) {
            var obj = rsl.data.enterprise;
            data.tradeName = obj.tradeName;
            data.phoneNumber = obj.phoneNumber;
            data.employeeCount = obj.employeeCount;
            var addressList = obj.sourceLogs;
            //mp.clearOverlays();
            if (obj.longitude != null && obj.latitude != null) {
                points.push({"lng": obj.longitude, "lat": obj.latitude});
                var enterp = new ChangeAddressIcon(new BMap.Point(obj.longitude, obj.latitude), obj.address, null, 'changeLocation.png');
                mp.addOverlay(enterp);
            }
            if (infoChangePaginationFlag == 1) {
                str += '<div class="lab cg-u">现地址：<span>' + obj.address + '</span></div>';
            }
            if (infoChangePaginationFlag == 0 || infoChangePaginationFlag == 2) {
                str += '<div class="lab cg-u">原地址：<span>' + obj.address + '</span></div>';
            }

            for (var i = 0; i < addressList.length; i++) {
                var addressObj = addressList[i];
                /* if(addressObj.address == obj.address && i == addressList.length -1) {
                 break;
                 }*/
                if (addressObj.longitude != null && addressObj.latitude != null) {
                    points.push({"lng": addressObj.longitude, "lat": addressObj.latitude});
                    var enterp = new ChangeAddressIcon(new BMap.Point(addressObj.longitude, addressObj.latitude), i + 1 + "、" + addressObj.address, null, 'oldAddressIcon.png');
                    mp.addOverlay(enterp);
                }
                if (infoChangePaginationFlag == 1) {
                    str += '<div class="lab cg-u">原地址：<span>' + addressObj.address + '</span></div>';
                } else {
                    str += '<div class="lab"><input type="radio" name="selectAddress" value="' + addressObj.address + '">变更地址(' + (i + 1) + ')：<span>' + addressObj.address + '</span></div>';
                }
            }
            //if(infoChangePaginationFlag == 1) {
            //    str = '<div class="lab"><sapn type="radio" name="selectAddress" value="'+addressObj.address+'">变更地址：<span>' + addressObj.address + '</span></div>';
            //}

            var view = mp.getViewport(eval(points));
            var mapZoom = view.zoom;
            var centerPoint = view.center;
            mp.centerAndZoom(centerPoint, mapZoom);
            for (var i = 0; i <= allOverMap.length - 1; i++) {
                allOverMap[i].hide();
            }
            if (points.length == 0) {
                alert("没有变更地址的坐标！");
            }

        }
    });

    //if (data.addressChange == 1 && data.majorBussinessChange != 1) {


    var buttonStr = "";
    if (infoChangePaginationFlag == 0) {
        //地址变更预警  取消变更按钮
        buttonStr += "" + '<button onclick="addressChangeCancelChange(\'' + data.id + '\')">取消变更</button><button onclick="ensureChange(\'' + data.id + '\',\'' + data.address + '\')">确认变更</button>';
    } else if (infoChangePaginationFlag == 1) {
        //历史变更查询 地址变更 取消变更按钮。
        buttonStr += "" + '<button onclick="cancelChange(\'' + data.id + '\')">取消变更</button>';
    } else {
        buttonStr += "" + '<button onclick="ensureChange(\'' + data.id + '\',\'' + data.address + '\')">确认变更</button>';
    }
    var tmpl = null;

    tmpl = '<div class="u-btn-big-box"><div class="u-btn JS-u-btn return-list" onclick="returnList(this)"><span class="fa-stack fa-lg">' +
        '<i class="fa fa-angle-left"></i>' +
        '</span>返回列表</div>' +
        '<div class="f-cb u-lab-box"><div class="u-lab-cg address-change">地址变更</div></div></div>' +
        '<div class="u-tt"><span hs-model="name"></span></div>' +
        '<div id="JS-building-show-content2"> ' + str +
        '<div id="JS-building-show-content2"> ' +
        ' <div class="lab">主营业务：<span hs-model="majorBusiness"></span></div>' +
        ' <div class="lab">行业：<span hs-model="tradeName"></span></div>' +
        ' <div class="lab">从业人员数量：<span hs-model="employeeCount"></span></div>' +
        ' <div class="lab">联系方式：<span hs-model="phoneNumber"></span></div>' + buttonStr +
        '</div>';
    enterpPoint = mp.getOverlays();
    $("#JS-show-content").html(tmpl);
    if (data.employeeCount == 0 || data.employeeCount == null) {
        data.employeeCount = "暂无";
    }
    HSUtil.dataBind($("#JS-show-content"), data);
    $("#JS-show-content .JS-u-btn").click(hideContent);
    $(".lab.cg-u").css("color", "#2EAAF2");
    clickEMapChange(data);
}

function ensureChange(id, address) {

    var inputs = document.getElementsByName("selectAddress");
    var chk;
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].checked == true) {
            chk = inputs[i].value;
        }
    }

    if (chk != undefined) {
        var gnl = confirm("确认变更?");
        if (gnl == true) {
            $.get(basePath + "/enterPrise/modifyInfo.do", "id=" + id + "&address=" + chk, function (obj) {
                hideContent();
                returnList();
                init();
            }, "json")
        }
    } else {
        alert("请选中地址.");
    }
}

function addressChangeCancelChange(id) {
    var inputs = document.getElementsByName("selectAddress");
    var chk;
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].checked == true) {
            chk = inputs[i].value;
        }
    }
    $.get(basePath + "/enterPrise/cancelModifyInfo.do", "id=" + id, function (obj) {
        if (obj.code == 200) {
            var gnl = confirm("取消变更?");
            if (gnl == true) {
                window.location.reload();
                return true;
            }
            else {
                return false;
            }
            hideContent();
            returnList();
        } else {
            alert(obj.msg);
        }
    }, "json")
}

function cancelChange(id) {
    var gnl = confirm("取消变更?");
    if (gnl == true) {
        $.get(basePath + "/enterPrise/addressCancelModifyInfo.do", "id=" + id, function (obj) {
            //hideContent();
            returnList();
            init();
        }, "json")
    }

}

//企业点击之后地图变化
function clickEMapChange(data) {

    if (data.addressChange != 1) {
        return;
    }

}

function hideContent() {
    if (blueCircleElement != null) {
        blueCircleElement.style.backgroundImage = "url(" + basePath + "/static/global/images/locationIcon.png)";
        blueCircleElement.style.overflow = "hidden";
        blueCircleElement = null;
        if (zIndexNumber != null) {
            if (circleOverply != null) {
                circleOverply.style.zIndex = zIndexNumber;
                circleOverply = null;
            }
            zIndexNumber = null;
        }
    }
    if (blueCircleElement2 != null) {
        blueCircleElement2.style.backgroundImage = "url(" + basePath + "/static/global/images/circleIcon.png)";
        blueCircleElement2 = null;
    }
    $("#JS-show-content").hide();
}
$(function () {
    $("#JS-show-content>.JS-u-btn").click(hideContent);
    $("#JS-building-show-content>.JS-u-btn").click(function () {
        if (blueCircleElement != null) {
            blueCircleElement.style.backgroundImage = "url(" + basePath + "/static/global/images/locationIcon.png)";
            blueCircleElement.style.overflow = "hidden";
            blueCircleElement = null;
        }
        $("#enterprise_body").hide();
        $("#JS-show-content").hide();
        $("#JS-building-show-content").hide();
        $("#build_body").show();
    });

    $("#logoImg").on('click', function () {
        window.location.href = basePath + '/index.do';
    })
})


//搜索条件点击事件
$(function () {
    $(".u-sch-item div a").click(function () {
        var _ = $(this);
        var content = _.html();
        var item = _.parent().prev();
        var id = _.data("id");
        item.find("em").html(content);
        item.find("em").attr("data-id", id);
        renderHangYe();
    });
    //搜索框添加回车按下提交事件
    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            $("#searchBtn").trigger("click");
        }
    });

    //搜索按钮初始化
    $("#searchBtn").click(function () {
        firstClick = true;
        var flag = $("#enterprise_body").attr("eb-flag");
        if (flag == 1) {
            initE();
        } else {
            initB();
        }
    });


    //企业和楼宇列表
    var divs = $("#selectEnOrBd").find("div");
    divs.click(function () {
        entepriseAndBuildingClick($(this));
    })
});

//楼宇和企业点击事件
function entepriseAndBuildingClick($jquery) {
    if ($jquery.html().indexOf("企业") != -1) {
        $(".u-btn-c4").mouseover(function () {
            $jquery.css("background", " #2eaaf2");
            $jquery.css("color", " #fff");
        });
        $(".u-btn-c4").mouseout(function () {
            $jquery.css("background", " #fff");
            $jquery.css("color", " #333");
        });
        for (var j = 0; j < 2; j++) {
            $(".u-sch-item" + ":eq(" + j + ")").css("overflow", "visible");
            $(".u-sch-item" + ":eq(" + j + ")").find(".u-btn-c4").css("background", "#fff");
        }
    } else {
        $(".u-btn-c4").unbind("mouseover");
        $(".u-btn-c4").unbind("mouseout");
        var identity = $("body").data("identity");
        if (identity == "index") {
            for (var j = 0; j < 2; j++) {
                $(".u-sch-item" + ":eq(" + j + ")").css("overflow", "hidden");
                $(".u-sch-item" + ":eq(" + j + ")").find(".u-btn-c4").css("background", "#ababab");
                $(".u-sch-item" + ":eq(" + j + ")").find(".u-btn-c4").css("color", "#333");
            }
        }

        $("#industries_menu").html("产业");
        $("#business_menu").html("行业");
        $("#category_menu").html("门类");
    }
    mp.clearOverlays();
    var currentDiv = $jquery;
    var firstDiv = "enterprise_body";
    var secondDiv = "build_body";
    var currentUShow = currentDiv.attr("u-show");
    hideContent();
    if (currentUShow != firstDiv) {
        infoChangePaginationFlag = 1;
        secondDiv = firstDiv;
        firstDiv = currentUShow;
        $("#enterprise_body").attr("eb-flag", "2");
        //initB();
    } else {
        //firstClick = true;
        $("#enterprise_body").attr("eb-flag", "1");
        infoChangePaginationFlag = 0;
        //initE();
    }
    init();
    currentDiv.attr("class", "u-tab active");
    if (currentDiv.attr("u-show") == "enterprise_body") {
        currentDiv.next().attr("class", "u-tab");
    } else {
        currentDiv.prev().attr("class", "u-tab");
    }
    showAndHiden("#" + firstDiv, "#" + secondDiv);
    $("#JS-building-show-content").hide();
}


function showAndHiden(showDiv, hideDiv) {
    $(showDiv).show();
    $(hideDiv).hide();
}

//初始化行业
function renderHangYe() {
    var industries_id = $("#industries_menu").attr("data-id");
    var business = new Classification(industries_id, null, 2);
    business.getList();
    renderEm();
}

function renderEm() {
    $(".u-sch-item div a").click(function () {
        var _ = $(this);
        var content = _.html();
        var item = _.parent().prev();
        var id = _.data("id");
        item.find("em").html(content);
        item.find("em").attr("data-id", id);
    });
}


//初始化门类
function renderMenLei() {
    var business_id = $("#business_menu").attr("data-id");
    var business = new Classification(business_id, null, 3);
    business.getList();
    renderEm();
    //var business_id = $("#business_menu").attr("data-id");
}

//初始化社区
function renderCommunity() {
    var street_id = $("#street_menu").attr("data-id");
    var street = new Classification(street_id, null, 5);
    street.getList();
    renderEm();
}

function getEnterpriseList(page_index, jq) {
    if (!page_index) {
        page_index = 0;
    }
    var scrollNumber = $("#enterprise_body").scrollTop();
    sessionStorage.setItem("scrollNumber", JSON.stringify(scrollNumber));
    //页面身份。index 表示首页  change 表示信息变更报警页面。
    var identity = $("body").data("identity");
    var categoryId = $("#category_menu").attr("data-id");
    var tradeId = $("#business_menu").attr("data-id");
    var industryId = $("#industries_menu").attr("data-id");
    var streetId = $("#street_menu").attr("data-id");
    var communityId = $("#community_menu").attr("data-id")
    var url = "";
    var QueryObject = {
        industryId: industryId,
        tradeId: tradeId,
        categoryId: categoryId,
        streetId: streetId,
        communityId: communityId,
        currentPage: page_index + 1
    };
    if (identity == "index") {//首页查询
        indexQuery(QueryObject)
    } else {
        enterpriseChangeQuery(QueryObject);
    }
}


//社区鼠标移入移出事件
function communityMouseOverAndOut(div, id, text) {

    div.onmouseover = (function (id) {

        return function () {
            if (id == null) {
                return;
            }
            var array = window.getElementByAttr("div", "hs-flag", id);
            if (array.length != 0) {
                array[0].style.backgroundImage = "url(" + basePath + "/static/global/images/hoverIcon.png)";
                array[0].style.overflow = "visible";
                var lastChild = array[0].lastChild;
                var oldContent = lastChild.innerHTML;
                var newContent = oldContent.substring(0, oldContent.indexOf("|") + 1) + text;
                lastChild.innerHTML = newContent;
            }
        }
    })(id);
    div.onmouseout = (function (id) {
        return function () {
            if (id == null) {
                return;
            }
            var array = window.getElementByAttr("div", "hs-flag", id);
            if (array.length != 0) {
                array[0].style.backgroundImage = "url(" + basePath + "/static/global/images/locationIcon.png)";
                array[0].style.overflow = "hidden";
            }
        }
    })(id);
}

//高亮显示街道
function mouseOverAboutCircle(div, streetId) {
    div.onmouseover = function () {
        if (streetId == null) {
            return;
        }
        var array = window.getElementByAttr('div', 'hs-flag', streetId);
        if (array.length != 0) {
            $("#JS-show-content").attr("cmd-st-de", streetId);
            array[0].style.backgroundImage = "url(" + basePath + "/static/global/images/circleHoverIcon.png)";
            zIndexNumber = array[0].style.zIndex;
            array[0].style.zIndex = 1;
        }
    };
    div.onmouseout = function () {
        if (streetId == null) {
            return;
        }
        var array = window.getElementByAttr('div', 'hs-flag', streetId);
        var $EnterpriseDetailPage = $("#JS-show-content");//企业详情div
        if ($EnterpriseDetailPage.css('display') == "none") {
            if (array.length != 0) {
                array[0].style.backgroundImage = "url(" + basePath + "/static/global/images/circleIcon.png)"
                if (zIndexNumber != null) {
                    array[0].style.zIndex = zIndexNumber;
                    zIndexNumber = null;
                }
            }
        }
    };
}
//var highlight = null;
function mouseOverAndOut(div, id) {

    div.onmouseover = (function (id) {
        return function () {
            if (id == null) {
                return;
            }
            var array = window.getElementByAttr("div", "hs-flag", id);
            if (array.length != 0) {
                array[0].style.backgroundImage = "url(" + basePath + "/static/global/images/hoverIcon.png)";
                zIndexNumber = array[0].style.zIndex;
                circleOverply = array[0];
                array[0].style.zIndex = 1;
                array[0].style.overflow = "visible";
            }
        }
    })(id);
    div.onmouseout = (function (id) {
        return function () {
            var $EnterpriseDetailPage = $("#JS-show-content");//企业详情div
            var $BuildingDetailPage = $("#JS-building-show-content");//楼宇详情div
            if ($EnterpriseDetailPage.css('display') == "none" && $BuildingDetailPage.css('display') == "none") {
                if (id == null) {
                    return;
                }
                var array = window.getElementByAttr("div", "hs-flag", id);
                if (array.length != 0) {
                    array[0].style.backgroundImage = "url(" + basePath + "/static/global/images/locationIcon.png)";
                    array[0].style.overflow = "hidden";
                    if (zIndexNumber != null) {
                        array[0].style.zIndex = zIndexNumber;
                        zIndexNumber = null;
                    }
                }
            }
        }
    })(id);
}

//首页查询
function indexQuery(QueryObject) {
    mouseenter=true;
    var conditionQueryBoxVal = $("#query_input").val();
    //查询当前显示企业还是楼宇 flag=1表示查询企业  flag=2表示查询楼宇
    var flag = $("#enterprise_body").attr("eb-flag");
    if (flag == 1) {
        QueryObject.enterpriseName = conditionQueryBoxVal;
        url = basePath + "/enterPrise/indexSearch.do";
    } else {
        delete QueryObject.industryId;
        delete QueryObject.categoryId;
        delete QueryObject.tradeId;
        QueryObject.buildingName = conditionQueryBoxVal;
        url = basePath + "/building/indexSearch.do";
    }
    var getList = $.get(url, QueryObject, "json");
    getList.done(function (obj) {
        if (flag == 1) {
            var $eBody = $("#enterprise_body");
            $eBody.find(".u-item").remove();
            var $pagination = $eBody.find(".pagination");
            //mp.clearOverlays();
            var points = [];
            var tempArray = obj.data.enterprises;
            for (var i = 0; i < tempArray.length; i++) {
                var tmp = tempArray[i];
                if (tmp.longitude != null && tmp.latitude != null) {
                    var obj2 = {"lng": tmp.longitude, "lat": tmp.latitude};
                    points.push(obj2);
                }
            }
            if (points.length != 0) {
                var view = mp.getViewport(eval(points));
                var mapZoom = view.zoom;
                var centerPoint = view.center;
                mp.centerAndZoom(centerPoint, mapZoom);//根据坐标自动初始化位子
            }
            myPoints=points;
            //var bool = $("#street_menu").attr("data-id") == "" && $("#community_menu").attr("data-id") == "" && $("#industries_menu").attr("data-id") == "";
            if (firstClick) {
                //初始化企业覆盖物
                mp.clearOverlays();
                var currentPage = (obj.data.currentPage - 1) * 10;
                for (var i = 0, tmp; tmp = obj.data.enterprises[i]; i++) {
                    if (tmp.longitude != null && tmp.latitude != null) {
                        var iconOverlay = new IconOverlay(new BMap.Point(tmp.longitude, tmp.latitude), (i + 1) + currentPage, tmp.name, enterpriseClick, tmp);
                        mp.addOverlay(iconOverlay);
                    }
                }
            } else {
                initializeStreet();
            }

            setNumberCenter();

            var currentPage = (obj.data.currentPage - 1) * 10;
            //如果地图显示的是二级页面-社区。
            //var communityFlag = $("#industries_menu").attr("data-id") == ""&&$("#street_menu").attr("data-id") != "" && $("#community_menu").attr("data-id") == "";
            for (var i = 0, tmp; tmp = obj.data.enterprises[i]; i++) {
                tmp.number = i + 1 + currentPage;
                var ent = new Enterprise(tmp, tmp, enterpriseClick);
                var div = ent.createDiv((i + 1) + currentPage);
                var id = tmp.id;
                if (firstClick) {
                    mouseOverAndOut(div, id);
                } else {
                    mouseOverAboutCircle(div, tmp.streetId);
                }
                $pagination.before(div);
            }
            if (obj.data.counts == 0 && obj.data.buildingCounts == 0) {
                $(".u-rst-1 ").eq(1).show();
                $(".u-rst-1 ").eq(0).hide();
            } else {
                $(".u-rst-1 ").eq(0).show();
                $(".u-rst-1 ").eq(1).hide();
                $("#enterpriseNumber").show();
                $("#enterpriseNumber2").show();
                $("#enterpriseNumber").html(obj.data.counts);
                //$("#buildingNumber").html(obj.data.buildingCounts+"座楼宇");
                $("#buildingNumber").hide();
                $("#buildingNumber2").hide();
            }
            hideContent();
        } else {
            var $eBody = $("#build_body");
            $eBody.find(".u-item").remove();
            var currentPage = (obj.data.currentPage - 1) * 10;
            var $pagination = $eBody.find(".pagination");
            for (var i = 0, tmp; tmp = obj.data.buildings[i]; i++) {
                tmp.number = i + 1 + currentPage;
                var ent = new Building(tmp, tmp, buildDetail);
                var div = ent.createBuildDiv(tmp.number);
                mouseOverAndOut(div, tmp.id);
                $pagination.before(div);
            }
            if (obj.data.counts == 0 && obj.data.enterpriseCounts == 0) {
                $(".u-rst-1 ").eq(1).show();
                $(".u-rst-1 ").eq(0).hide();
            } else {
                $(".u-rst-1 ").eq(0).show();
                $(".u-rst-1 ").eq(1).hide();
                //$("#enterpriseNumber").html(obj.data.enterpriseCounts);
                $("#enterpriseNumber").hide();
                $("#enterpriseNumber2").hide();
                $("#buildingNumber").show();
                $("#buildingNumber2").show();
                $("#buildingNumber").html(obj.data.counts);
                loadBuildingOverlay(obj);//加载楼宇覆盖物
            }
            hideContent();
        }
        setNumberCenter();
        var scrollNumber = ( eval("(" + sessionStorage.getItem("scrollNumber") + ")"));//点击页码，滚动条位置不变
        if (scrollNumber != null) {
            $("#enterprise_body").scrollTop(scrollNumber);
        }
    });

}
//楼宇以及企业图标编号超过10的企业图标中的数字居中调整
function setNumberCenter() {
    var $div = $(".u-icon-hs");
    var indexNum;
    for (var j = 0; j < $div.length; j++) {
        indexNum = $(".u-icon-hs").eq(j).find("span").html();
        if (indexNum >= 10) {
            $(".u-icon-hs").eq(j).css("padding-left", "3px");
        }
    }
}


//加载楼宇覆盖物
function loadBuildingOverlay(rlt) {
    mp.clearOverlays();
    var currentPage = (rlt.data.currentPage - 1) * 10;
    for (var i = 0, tmp; tmp = rlt.data.buildings[i]; i++) {
        if (tmp.longitude != null && tmp.latitude != null) {
            var rectangle = new IconOverlay(new BMap.Point(tmp.longitude, tmp.latitude), (i + 1) + currentPage, tmp.name, buildDetail, tmp);
            mp.addOverlay(rectangle);
        }
    }
}

//加载信息变更报警覆盖物
function loadInfoChangeOverlay(obj) {
    var currentPage = (obj.data.currentPage - 1) * 10;
    for (var i = 0, tmp; tmp = obj.data.changeEnterprises[i]; i++) {
        var rectangle = new IconOverlay(new BMap.Point(tmp.longitude, tmp.latitude), (i + 1) + currentPage, tmp.name, enterpriseClick, tmp);
        mp.addOverlay(rectangle);
    }
    allOverMap = mp.getOverlays();
    centerPoint = mp.getCenter();
    zoomScale = mp.getZoom();
    setNumberCenter();
}

//企业变更页查询
function enterpriseChangeQuery(QueryObject) {
    //查询当前显示企业还是楼宇 flag=1表示查询企业  flag=2表示查询楼宇
    var flag = $("#enterprise_body").attr("eb-flag");
    var url = basePath + "/enterPrise/showChanges.do";
    QueryObject.enterpriseName = $("#query_input").val().trim();
    if (flag == 1) {
        QueryObject.isModifyLog = 0;
    } else {
        var $addressChangeButton = $("#addressChangeButton");
        var cancelButton = document.getElementById("cancelButton");
        $addressChangeButton.css("background-color", "#2eaaf2");
        cancelButton.style.backgroundColor = "#DDDDDD";
        var startTime = getDateTime()[0].startTime;
        var endTime = getDateTime()[1].endTime;
        QueryObject.startTime = startTime;
        QueryObject.endTime = endTime;
        QueryObject.isModifyLog = 1;
    }
    var getList = $.get(url, QueryObject, "json");
    getList.done(function (obj) {
        //数据条数
        var dataCount = obj.data.changeEnterprises.length;
        var $eBody = $("#enterprise_body");
        var $build_body = $("#build_body");
        /*sign*/

        if (flag == 1) {
            var e1 = $eBody.find(".noEnterMessage");//清空
            var e2 = $eBody.find('.u-item');
            e1.remove();
            e2.remove();
        } else {
            var e1 = $build_body.find(".noEnterMessage");//清空
            var e2 = $build_body.find('.u-item');
            e1.remove();
            e2.remove();
        }

        if (flag == 1) {
            $eBody.find(".u-item").remove();
            var $pagination = $eBody.find(".pagination");
            var currentPage = (obj.data.currentPage - 1) * 10;
            for (var i = 0, tmp; tmp = obj.data.changeEnterprises[i]; i++) {
                tmp.number = (i + 1) + currentPage;
                var ent = new Enterprise2(tmp, tmp, enterpriseClick);
                var div = ent.createDiv(tmp.number);
                var id = tmp.id;
                mouseOverAndOut(div, id);
                $pagination.before(div);
            }
            hideContent();
        } else {
            $build_body.find(".u-item").remove();
            var $pagination = $build_body.find(".pagination");
            var currentPage = (obj.data.currentPage - 1) * 10;
            for (var i = 0, tmp; tmp = obj.data.changeEnterprises[i]; i++) {
                tmp.number = (i + 1) + currentPage;
                var ent = new Enterprise2(tmp, tmp, enterpriseClick);
                var div = ent.createDiv(tmp.number);
                var id = tmp.id;
                mouseOverAndOut(div, id);
                $pagination.before(div);
            }
            hideContent();
        }
        mp.clearOverlays();
        loadInfoChangeOverlay(obj);
        if (dataCount <= 10) {
            $("#JS-b-pagination").hide();
        }
    });


    initializeStreet();
}

function init() {
    var flag = $("#enterprise_body").attr("eb-flag");
    if (flag == 1) {
        initE();
    } else {
        initB();
    }
}
//sign
function initE() {
    var identity = $("body").data("identity");

    var categoryId = $("#category_menu").attr("data-id");
    var tradeId = $("#business_menu").attr("data-id");
    var industryId = $("#industries_menu").attr("data-id");
    var streetId = $("#street_menu").attr("data-id");
    var communityId = $("#community_menu").attr("data-id");
    var startTime = getDateTime()[0].startTime;
    var endTime = getDateTime()[1].endTime;
    var QueryObject = {
        industryId: industryId,
        tradeId: tradeId,
        categoryId: categoryId,
        streetId: streetId,
        communityId: communityId,
        startTime: startTime,
        endTime: endTime,
        enterpriseName: $("#query_input").val().trim()
    };
    //QueryObject.changeCountsCondition = 3;

    var flag = $("#enterprise_body").attr("eb-flag");
    if (identity == "index") {
        url = basePath + "/enterPrise/selectEnterPriseCounts.do";
    } else {
        if (flag == 1) {
            QueryObject.startTime = null;
            QueryObject.endTime = null;
            QueryObject.isModifyLog = 0;
        }
        //if(infoChangePaginationFlag==0){
        QueryObject.isModifyLog = infoChangePaginationFlag;
        //}else if(infoChangePaginationFlag==1){
        //    QueryObject.isModifyLog = 1;
        //}else if(infoChangePaginationFlag==2){
        //    QueryObject.isModifyLog = 2;
        //}

        //else {
        //    QueryObject.majorBussinessChange = 1;
        //}
        //QueryObject.isModifyLog = 0;
        url = basePath + "/enterPrise/showChangesCounts.do";
    }

    var getList = $.get(url, QueryObject, "json");
    getList.done(function (obj) {
        if (identity == "index") {
            infoCount(obj);
            ePagination(obj.data);
        } else {
            //var Object = {};
            //enterpriseChangeQuery(Object);

            $("#addressChangeWarningCount").html("共找到变更企业" + obj.data + "家");
            //if(infoChangePaginationFlag==0){
            changeFirstPagination(obj.data);
            //}else {
            //    bPagination(total, addressOrCance);
            //}
        }
    });

}

/*信息变更企业统计*/
function infoCount(obj) {
    if (obj.changeAddressOrbusinessEnterpriseCounts == "undefined") {
        obj.changeAddressOrbusinessEnterpriseCounts = "0";
    }
    if (obj.changeAddressEnterpriseCounts == "undefined") {
        obj.changeAddressEnterpriseCounts = "0";
    }
    if (obj.changeBusinessEnterpriseCounts == "undefined") {
        obj.changeBusinessEnterpriseCounts = "0";
    }
    $("#enterpriseNumber").html(obj.data.changeAddressOrbusinessEnterpriseCounts);
    $("#addressChangeNumber").html(obj.data.changeAddressEnterpriseCounts);
    $("#businessChangeNumber").html(obj.data.changeBusinessEnterpriseCounts);
}

function initB() {
    var categoryId = $("#category_menu").attr("data-id");
    var tradeId = $("#business_menu").attr("data-id");
    var industryId = $("#industries_menu").attr("data-id");
    var streetId = $("#street_menu").attr("data-id");
    var communityId = $("#community_menu").attr("data-id");
    var startTime = getDateTime()[0].startTime;
    var endTime = getDateTime()[1].endTime;
    var url = "";
    var QueryObject = {
        industryId: industryId,
        tradeId: tradeId,
        categoryId: categoryId,
        streetId: streetId,
        communityId: communityId,
        startTime: startTime,
        endTime: endTime,
        buildingName: $("#query_input").val().trim()
    };
    QueryObject.changeCountsCondition = 3;
    QueryObject.isModifyLog = infoChangePaginationFlag;
    var identity = $("body").data("identity");
    var flag = $("#enterprise_body").attr("eb-flag");

    if (infoChangePaginationFlag == 2) {
        QueryObject.changeCountsCondition = null;
    }

    if (identity == "index") {
        if (flag == 1) {
            url = basePath + "/enterPrise/selectEnterPriseCounts.do";//查询企业
        } else {
            url = basePath + "/building/selectBuildingCounts.do";//查询楼宇
        }
    } else {
        QueryObject.addressChange = 1;
        url = basePath + "/enterPrise/showChangesCounts.do";
    }
    var getList = $.get(url, QueryObject, "json");
    getList.done(function (obj) {
        if (identity == "index") {
            infoCount(obj);
            bPagination(obj.data, getEnterpriseList);
        } else {
            if (infoChangePaginationFlag == 1) {
                $("#enterpriseCount").html("共找到变更企业" + obj.data + "家");
                bPagination(obj.data, addressChangeButton);
            } else if (infoChangePaginationFlag == 2) {
                $("#enterpriseCount").html("共找到变更企业" + obj.data + "家");
                bPagination(obj.data, cancelButtonFunc);
            }
        }
    });
}
$(function () {
    initE();
});

function ePagination(total) {
    $("#JS-e-pagination").pagination(total, {
        num_edge_entries: 1, //边缘页数
        num_display_entries: 4, //主体页数
        callback: getEnterpriseList,
        items_per_page: 10 //每页显示1项
    });
    paginationShowOrHide(total);
}
function bPagination(total, func) {
    $("#JS-b-pagination").pagination(total, {
        num_edge_entries: 1, //边缘页数
        num_display_entries: 4, //主体页数
        callback: func,
        items_per_page: 10 //每页显示1项
    });
    paginationShowOrHide(total);
}
function changeFirstPagination(total) {
    $("#JS-e-pagination").pagination(total, {
        num_edge_entries: 1, //边缘页数
        num_display_entries: 4, //主体页数
        callback: getEnterpriseList,
        items_per_page: 10 //每页显示1项
    });

    paginationShowOrHide(total);
}
//sign
function paginationShowOrHide(total) {
    if (total < 10) {
        $("#JS-e-pagination").hide();
        $("#JS-b-pagination").hide();
    } else {
        $("#JS-e-pagination").show();
        $("#JS-b-pagination").show();
    }

}
//楼宇内企业分页
function buildInsiderEnterprise(total) {
    if (total <= 10) {
        $("#JS-b-e-pagination").hide();
    } else {
        $("#JS-b-e-pagination").show();
    }
    $("#JS-b-e-pagination").pagination(total, {
        num_edge_entries: 1, //边缘页数
        num_display_entries: 4, //主体页数
        callback: buildDetailEnterpriseList,
        items_per_page: 10 //每页显示1项
    });
}
//楼宇点击详情
function buildDetail(obj) {
    sessionStorage.setItem("buildObj", JSON.stringify(obj));
    var categoryId = $("#category_menu").attr("data-id");
    var tradeId = $("#business_menu").attr("data-id");
    var industryId = $("#industries_menu").attr("data-id");
    var streetId = $("#street_menu").attr("data-id");
    var communityId = $("#community_menu").attr("data-id");
    var QueryObject = {
        industryId: industryId,
        tradeId: tradeId,
        categoryId: categoryId,
        streetId: streetId,
        communityId: communityId,
        id: obj.id
    };
    var $getList = $.get(basePath + "/enterPrise/selectEnterPriseCounts.do", QueryObject);
    $getList.done(function (rsl) {
        buildInsiderEnterprise(rsl.data);
    })
    $("#JS-show-content").hide();
    var array = window.getElementByAttr("div", "hs-flag", obj.id);
    if (array.length != 0) {
        //blueCircleElement = array[0];
        array[0].style.backgroundImage = "url(" + basePath + "/static/global/images/hoverIcon.png)";
        array[0].style.overflow = "visible";
    }
}

function buildDetailEnterpriseList(page_index, jq) {
    if (!page_index) {
        page_index = 0;
    }
    var categoryId = $("#category_menu").attr("data-id");
    var tradeId = $("#business_menu").attr("data-id");
    var industryId = $("#industries_menu").attr("data-id");
    var streetId = $("#street_menu").attr("data-id");
    var communityId = $("#community_menu").attr("data-id");
    var queryInput = $("#query_input").val();


    var obj = JSON.parse(sessionStorage.getItem("buildObj"));
    var QueryObject = {
        industryId: industryId,
        tradeId: tradeId,
        categoryId: categoryId,
        streetId: streetId,
        communityId: communityId,
        id: obj.id,
        currentPage: page_index + 1
    };
    $("#build_body").hide();
    var $building_show_content = $("#JS-building-show-content");
    var $getList = $.get(basePath + "/building/searchById.do", QueryObject);
    $getList.done(function (rst) {
        obj.tradeName = rst.tradeName;
        //obj.employeeCounts =rst.employeeCounts;
        obj.phoneNumber = rst.phoneNumber;
        obj.registerTime = rst.registerTime;
        obj.registerCapital = rst.registerCapital;
        obj.openFlag = rst.openFlag;

        //if (rst.data.building.changeEnterprises != null) {
        if (rst.data.building == null) {
            obj.enterpriseSize = "0 家";
            obj.employeeCounts = "暂无";
        } else {
            var list = rst.data.building.enterprises;
            obj.enterpriseSize = obj.enterpriseCounts + "家";
            if (obj.employeeCounts == 0) {
                obj.employeeCounts = "暂无";
            }
        }
        HSUtil.dataBind("#JS-building-show-content", obj);
        var temp = $("#JS-building-show-content").find(".u-item");
        for (var i = 0; i < temp.length; i++) {
            temp[i].remove();
        }
        var currentPage = rst.data.currentPage;
        for (var i = 0, temp; temp = list[i]; i++) {
            var enter = new Enterprise2(temp, temp, enterpriseClick);
            $("#JS-b-e-pagination").before(enter.createDiv((i) + currentPage));
        }
        //}
        $(".addressChangeInfo").remove();
    });
    //显示分页
    $("#build_insider_enterprise_body").show();
    $building_show_content.show();
}

$(function () {
    //初始化产业
    var getChanYe = $.get(basePath + "/category/getIndustries.do", "json");
    getChanYe.done(function (o) {
        var $industries_div = $("#industries_div");
        $industries_div.html("");
        var allTem1 = {name: "全部产业", id: null};
        var allIndustries = new ALabel(allTem1, renderHangYe, 1);
        $industries_div.append(allIndustries.create());
        for (var i = 0, tem; tem = o.data[i]; i++) {
            var a = new ALabel(tem, renderHangYe, 1);
            $industries_div.append(a.create());
        }
    });
})

function initializeStreet() {

    mouseenter=false;
    //初始化街道
    var getChanYe = $.get(basePath + "/street/searchAll.do", "json");
    getChanYe.done(function (o) {
        var $industries_div = $("#street_div");
        $industries_div.html("");
        var identity = $("body").data("identity");
        var allTem = {name: "全部街道", id: ""};
        var allTema = new ALabel(allTem, revertStreet, 4);
        $industries_div.append(allTema.create());
        if (identity == "index") {
            var points = [];
            for (var i = 0, tmp; tmp = o.data[i]; i++) {
                if (tmp.longitude != null && tmp.latitude != null) {
                    var obj = {"lng": tmp.longitude, "lat": tmp.latitude};
                    points.push(obj);
                }
            }
            if (points.length != 0) {
                var view = mp.getViewport(eval(points));
                var mapZoom = view.zoom;
                var centerPoint = view.center;
                mp.centerAndZoom(centerPoint, mapZoom);//根据坐标自动初始化位子
            }
        }
        for (var i = 0, tem; tem = o.data[i]; i++) {
            if (identity == "index") {
                var str = tem.name + "<br>" + tem.enterpriseCounts + "家企业";
                var circle = new CircleCustomOverlay(new BMap.Point(tem.longitude, tem.latitude), str, str, streetClick, tem);
                mp.addOverlay(circle);
            }
            var a = new ALabel(tem, null, 4);
            $industries_div.append(a.create());
        }
    });
}

function revertStreet() {
    mp.clearOverlays();
    var $community = $("#community_menu");
    $("#street_menu").attr("data-id", "");
    $community.attr("data-id", "");
    $community.parent().next().html("");
    $community.html("社区");
    init();
    //initializeStreet();
    //getEnterpriseList();
}

function ALabel(object, callback, identity) {
    this._data = object;
    this._callback = callback;
    this._identity = identity;
}
ALabel.prototype = {};
ALabel.prototype.create = function () {
    var that = this;
    var a = document.createElement("a");
    a.setAttribute("href", "javascript:;");
    a.setAttribute("title", that._data.name);
    a.setAttribute("data-id", that._data.id);
    a.innerHTML = that._data.name;
    a.onclick = function () {
        firstClick = true;
        //给下拉框中的内容和data-id赋值
        categoryClickEvent($(this));
        if (that._identity == 1) {
            $("#business_menu").html("行业");
            $("#business_menu").attr("data-id", "");
            $("#category_menu").html("门类");
            $("#category_menu").attr("data-id", "");
            mp.clearOverlays();
            init();
        }
        if (that._identity == 2) {
            $("#category_menu").html("门类");
            $("#category_menu").attr("data-id", "");
            init();
        }
        if (that._identity == 3) {
            init();
        }
        var street_dataId = $("#street_menu").attr("data-id");
        if (that._identity == 4 && street_dataId != "") {
            $("#community_menu").html("社区");
            $("#community_menu").attr("data-id", "");
            streetClick(that._data);
            sessionStorage.setItem("treetObj", JSON.stringify(that._data));
        }
        var community_dataId = $("#community_menu").attr("data-id");
        if (that._identity == 5 && community_dataId != "") {
            communityClick(that._data);
        }
        if (that._callback != null) {
            that._callback();
        }
    };
    return a;
};

function Classification(resourceId, callback, type) {
    this._resourceId = resourceId;
    this._callback = callback;
    this._type = type;
}
Classification.prototype = {};

//给下拉框的内容和data-id赋值
function categoryClickEvent(_) {
    var chooseContent;
    var content = _.html();
    var item = _.parent().prev();
    var id = _.data("id");
    item.find("em").html(content);
    item.find("em").attr("data-id", id);
    item.find("em").attr("title", item.find("em").attr("data-id", id).text());
    /*下拉框选项内容长度大于6则自动截取*/
    if (item.find("em").attr("data-id", id).text().length > 6) {
        chooseContent = item.find("em").text().substr(0, 6);
        item.find("em").text(chooseContent);
    }

}

Classification.prototype.getList = function () {
    var that = this;
    var urlPart = ""
    var target_div;
    if (that._type == 2) {
        urlPart += "/category/getTradeById.do";
        target_div = $("#business_div");
    } else if (that._type == 3) {
        urlPart += "/category/getCategoryById.do";
        target_div = $("#category_div");
    } else if (that._type == 5) {
        urlPart += "/street/searchById.do";
        target_div = $("#community_div");
    }
    if (that._resourceId == "") {
        that._resourceId == null;
    }
    //根据产业查找门业集合
    var getList = $.get(basePath + urlPart, {id: that._resourceId}, "json");
    getList.done(function (rst) {
        target_div.html("");
        if (that._type == 5) {
            var tem = {name: "全部社区", id: "0"};
            var a = new ALabel(tem, revertCommunity, that._type);
            target_div.append(a.create());
            for (var i = 0, tem; i < rst.data.street.coummunitys.length; i++) {
                tem = rst.data.street.coummunitys[i];
                var a = null;
                a = new ALabel(tem, null, that._type);
                target_div.append(a.create());
            }

            var identity = $("body").data("identity");
            if (identity == "index") {
                //收集坐标用于自动计算位子。
                var points = [];
                for (var i = 0, tmp; tmp = rst.data.street.coummunitys[i]; i++) {
                    if (tmp.longitude != null && tmp.latitude != null) {
                        var obj = {"lng": tmp.longitude, "lat": tmp.latitude};
                        points.push(obj);
                    }
                }
                if (points.length != 0) {
                    var view = mp.getViewport(eval(points));
                    var mapZoom = view.zoom;
                    var centerPoint = view.center;
                    mp.centerAndZoom(centerPoint, mapZoom);//根据坐标自动初始化位子
                }

                //初始化社区地图覆盖物
                var len;
                mp.clearOverlays();
                var currentPage = (rst.data.currentPage - 1) * 10;
                for (var i = 0, tmp; tmp = rst.data.street.coummunitys[i]; i++) {
                    len = tmp.enterpriseCounts;
                    if (tmp.longitude != null && tmp.latitude != null) {
                        var iconOverlay = new IconOverlay(new BMap.Point(tmp.longitude, tmp.latitude), (i + 1) + currentPage, tmp.name + "|" + len + "家企业", communityClick, tmp);
                        // mp.addOverlay(iconOverlay);
                    }
                }
            }
        }
        else {
            if (rst.data == null) {
                return;
            }
            for (var i = 0, tem; i < rst.data.length; i++) {
                tem = rst.data[i];
                var a = null;
                a = new ALabel(tem, renderMenLei, that._type);
                target_div.append(a.create());
            }
        }
    })
};

function revertCommunity() {
    var $communityMenu = $("#community_menu");
    $communityMenu.html("全部社区");
    $communityMenu.attr("data-id", "");
    streetClick(JSON.parse(sessionStorage.getItem("treetObj")));
}

//热力图展示
function showHeatMap() {
    var overlays = mp.getOverlays();
    for (var i = 0; i < overlays.length; i++) {
        overlays[i].hide();
    }
    var points = [];

    $.ajax({
        type: "get",
        url: basePath + "/building/indexSearch.do",
        data: {"limit": 1000},
        dataType: "json",
        success: function (result) {
            var buildings = result.data.buildings;
            for (var i = 0; i < buildings.length; i++) {
                var lng = buildings[i].longitude;
                var lat = buildings[i].latitude;
                var count = Math.random() * 100;
                points.push({"lng": lng, "lat": lat, "count": count});
            }
            heatmapOverlay = new BMapLib.HeatmapOverlay({"radius": 30});
            mp.addOverlay(heatmapOverlay);
            heatmapOverlay.setDataSet({data: points, max: 100});

        },
        error: function () {
            alert("error");
        }
    });
}

//点击变更地址div找到地图上对应位置
//function addressChange(longitude, latitude) {
//    if (latitude != null && longitude != null) {
//        mp.clearOverlays();
//        mp.centerAndZoom(new BMap.Point(latitude, longitude), 13);
//        mp.enableScrollWheelZoom();
//        //mp.clearOverlays();
//        var enterprise = new EnterpriseCustomOverlay(new BMap.Point(latitude, longitude), null, null, null, null);
//        mp.addOverlay(enterprise);
//    } else {
//        alert("没有找到该位置坐标");
//    }
//
//
//};
function myDeleteDeal() {
    var inputValue = $("#inputValue").val();
    if (inputValue != "") {
        $("input[name='test']").val("").focus();

    }
};
$(function () {
    var date = new Date;
    var year = date.getFullYear();
    var month = date.getMonth() + 1;

    var startDate = (year - 10).toString() + '-' + (month + 2).toString();
    var endDate = year.toString() + '-' + (month + 2).toString();

    $('.form_date').datetimepicker({
        format: 'yyyy-mm',
        weekStart: 1,
        autoclose: true,
        minView: 'month',
        startDate: startDate,
        endDate: endDate,
        todayHighlight: 1,
        startView: 3,
        minView: 3,
        forceParse: false,
        language: 'zh-CN'
    });

    /*$('.form_date').datetimepicker('setStartDate', '2012-01-01');*/

    $('#startDate')
        .datetimepicker()
        .on('changeDate', function (ev) {
            changeDateFlag = 0;
            mp.clearOverlays();
            var startDate = formatCSTDate(ev.date, "yyyy-M");
            $('#dtp_input_s').val(startDate);
            init();
        });

    $('#endDate')
        .datetimepicker()
        .on('changeDate', function (ev) {
            changeDateFlag = 1;
            mp.clearOverlays();
            var endDate = formatCSTDate(ev.date, "yyyy-M");
            $('#dtp_input_e').val(endDate);
            init();
        });
});

function addressOrCance() {
    if (addressOrCanceFlag == 2) {
        var page_index = null;
        cancelButtonFunc(page_index);
    } else if (addressOrCanceFlag == 1) {
        var page_index = null;
        addressChangeButton(page_index);
    }
}
//获得日期时间
function getDateTime() {
    var startTime = $('#dtp_input_s').val();
    var endTime = $("#dtp_input_e").val();

    var date = new Date;
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    if (startTime == '' && endTime == '') {
        endTime = year.toString() + '-' + month.toString();
        if (parseInt(month) == 1) {
            startTime = (year - 1).toString() + '-12'
        } else {
            startTime = year.toString() + '-' + (parseInt(month) - 1).toString();
        }
    }
    if (startTime != undefined) {
        var begin = new Date(startTime);
        var end = new Date(endTime);
        var arry = startTime.split('-');
        var monthSt = parseInt(arry[1]);
    }
    if (changeDateFlag == 0 && (end - begin <= 0 )) {
        endTime = startTime;
    } else if (changeDateFlag == 1 && (end - begin <= 0 )) {
        startTime = endTime;
    }

    $("#endDate").find('input').val(endTime);
    $("#startDate").find('input').val(startTime);
    $('#dtp_input_s').val(startTime);
    $('#dtp_input_e').val(endTime);
    var dateInfo = [{'startTime': startTime}, {'endTime': endTime}];
    return dateInfo;
}

//格式化CST日期的字串
function formatCSTDate(strDate, format) {
    return formatDate(new Date(strDate), format);
}
function formatDate(date, format) {
    var paddNum = function (num) {
        num += "";
        return num.replace(/^(\d)$/, "0$1");
    }
    //指定格式字符
    var cfg = {
        yyyy: date.getFullYear() //年 : 4位
        , yy: date.getFullYear().toString().substring(2)//年 : 2位
        , M: date.getMonth() + 1  //月 : 如果1位的时候不补0
        , MM: paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
        , d: date.getDate()   //日 : 如果1位的时候不补0
        , dd: paddNum(date.getDate())//日 : 如果1位的时候补0
        , hh: date.getHours()  //时
        , mm: date.getMinutes() //分
        , ss: date.getSeconds() //秒
    }
    format || (format = "yyyy-MM-dd hh:mm:ss");
    return format.replace(/([a-z])(\1)*/ig, function (m) {
        return cfg[m];
    });
}

$(function () {
    var a = $("#clearInput");
    a.mouseover(function () {
        a.css("background-image", "url(" + basePath + "/static/global/x1.png)");
    });
    a.mouseout(function () {
        a.css("background-image", "url(" + basePath + "/static/global/x.png)");
    });
})

//点击返回列表触发鼠标点击事件
function returnList() {
    mp.centerAndZoom(new BMap.Point(centerPoint.lng, centerPoint.lat), zoomScale);
    for (var i = 0; i <= allOverMap.length - 1; i++) {
        allOverMap[i].show();
    }
    for (var j = allOverMap.length; j <= enterpPoint.length - 1; j++) {
        mp.removeOverlay(enterpPoint[j]);
    }
}

$(function () {
    var $addressChangeButton = $("#addressChangeButton");
    var cancelButton = document.getElementById("cancelButton");
    if ($addressChangeButton != null) {
        $addressChangeButton.click(function () {
            $addressChangeButton.css("background-color", "#2eaaf2");
            cancelButton.style.backgroundColor = "#DDDDDD";
            infoChangePaginationFlag = 1;
            init();
            mp.clearOverlays();
        });
    }

    if (cancelButton != null) {
        cancelButton.addEventListener("click", function () {
            $addressChangeButton.css("background-color", "#DDDDDD");
            cancelButton.style.backgroundColor = "#2eaaf2";
            infoChangePaginationFlag = 2;
            init();
            //var that = $(this);
            //var page_index = null;
            //cancelButtonFunc(page_index);
        });
    }
})

function cancelButtonFunc(page_index) {
    if (!page_index) {
        page_index = 0;
    }
    addressOrCanceFlag = 2;
    var categoryId = $("#category_menu").attr("data-id");
    var tradeId = $("#business_menu").attr("data-id");
    var industryId = $("#industries_menu").attr("data-id");
    var streetId = $("#street_menu").attr("data-id");
    var communityId = $("#community_menu").attr("data-id");
    var QueryObject = {
        industryId: industryId,
        tradeId: tradeId,
        categoryId: categoryId,
        streetId: streetId,
        communityId: communityId,
        currentPage: page_index + 1,
        isModifyLog: 2
    };

    var startTime = getDateTime()[0].startTime;
    var endTime = getDateTime()[1].endTime;
    QueryObject.startTime = startTime;
    QueryObject.endTime = endTime;
    if (infoChangePaginationFlag == 2) {
        var labelMOdify = $("#build_body").find('nobr');
    }
    var getList = $.get(basePath + "/enterPrise/showChanges.do", QueryObject, "json");
    getList.done(function (obj) {
        //数据条数

        var dataCount = obj.data.changeEnterprises.length;
        var $eBody = $("#enterprise_body");
        var $build_body = $("#build_body");
        /*sign*/

        var flag = $("#enterprise_body").attr("eb-flag");

        if (flag == 1) {
            var e1 = $eBody.find(".noEnterMessage");//清空
            var e2 = $eBody.find('.u-item');
            var e3 = $build_body.find('.u-item');
            e1.remove();
            e2.remove();
        } else {
            var e1 = $build_body.find(".noEnterMessage");//清空
            var e2 = $build_body.find('.u-item');
            e1.remove();
            e2.remove();
        }

        if (flag == 1) {
            $eBody.find(".u-item").remove();
            var $pagination = $eBody.find(".pagination");
            var currentPage = (obj.data.currentPage - 1) * 10;
            for (var i = 0, tmp; tmp = obj.data.changeEnterprises[i]; i++) {
                tmp.number = (i + 1) + currentPage;
                var ent = new Enterprise2(tmp, tmp, enterpriseClick);
                var div = ent.createDiv(tmp.number);
                var id = tmp.id;
                mouseOverAndOut(div, id);
                $pagination.before(div);
            }
            hideContent();
        } else {
            $build_body.find(".u-item").remove();
            var $pagination = $build_body.find(".pagination");
            var currentPage = (obj.data.currentPage - 1) * 10;
            for (var i = 0, tmp; tmp = obj.data.changeEnterprises[i]; i++) {
                tmp.number = (i + 1) + currentPage;
                var ent = new Enterprise2(tmp, tmp, enterpriseClick);
                var div = ent.createDiv(tmp.number);
                var id = tmp.id;
                mouseOverAndOut(div, id);
                $pagination.before(div);
            }
            hideContent();
        }
        $(".addressChangeInfo").css("text-decoration", "line-through");
        mp.clearOverlays();
        loadInfoChangeOverlay(obj);
        $(".addressChangeInfo").css("text-decoration", "line-through");
    });
    initializeStreet();
}

function addressChangeButton(page_index) {
    infoChangePaginationFlag = 1;
    if (!page_index) {
        page_index = 0;
    }
    addressOrCanceFlag = 1;
    var categoryId = $("#category_menu").attr("data-id");
    var tradeId = $("#business_menu").attr("data-id");
    var industryId = $("#industries_menu").attr("data-id");

    var streetId = $("#street_menu").attr("data-id");
    var communityId = $("#community_menu").attr("data-id")
    var QueryObject = {
        industryId: industryId,
        tradeId: tradeId,
        categoryId: categoryId,
        streetId: streetId,
        communityId: communityId,
        currentPage: page_index + 1
    };

    enterpriseChangeQuery(QueryObject);
    //entepriseAndBuildingClick($(".u-tab.active"));//刷新数据。
}