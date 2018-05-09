/**
 * Created by honshe on 2016/5/30.
 */
// 百度地图API功能
var mp = new BMap.Map("allmap", {enableMapClick: false});
mp.centerAndZoom(new BMap.Point(116.293281, 39.864896), 13);
mp.enableScrollWheelZoom();

// 根据元素属性值查找对象
function getElementByAttr(tag,attr,value) {
    var aElements=document.getElementsByTagName(tag);
    var aEle=[];
    for(var i=0;i<aElements.length;i++)
    {
        if(aElements[i].getAttribute(attr)==value)
            aEle.push( aElements[i] );
    }
    return aEle;
}

// 复杂的自定义覆盖物
function CircleCustomOverlay(point, text, mouseoverText, funt, object) {
    this._point = point;
    this._text = text;
    this._overText = mouseoverText;
    this._funt = funt;
    this._object = object;
}
CircleCustomOverlay.prototype = new BMap.Overlay();
CircleCustomOverlay.prototype.initialize = function (map) {
    this._map = map;
    var div = this._div = document.createElement("div");
    div.style.width="70px";
    div.style.height = "80px";
    div.style.backgroundImage="url("+basePath+"/static/global/images/circleIcon.png)"
    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
    div.setAttribute("class", "u-circle");

    var span = this._span = document.createElement("span");
    div.appendChild(span);
    span.style.color = "white";
    span.innerHTML = this._text;
    var that = this;

    var arrow = this._arrow = document.createElement("div");
    arrow.style.position = "absolute";
    arrow.style.top = "22px";
    arrow.style.left = "12px";
    arrow.style.overflow = "hidden";
    div.appendChild(arrow);

    div.onclick = function () {
        that._funt(that._object);
    }
    /*社区移入移出*/
    div.onmouseover = function () {
        div.style.backgroundImage="url("+basePath+"/static/global/images/circleHoverIcon.png)"
        this.getElementsByTagName("span")[0].innerHTML = that._overText;

        if (zIndexNumber != null) {
            if(circleOverply!=null){
                circleOverply.style.zIndex = zIndexNumber;
            }else {
                circleOverply.style.zIndex = -10000;
            }
        }
        zIndexNumber = div.style.zIndex ;
        circleOverply = div;
        div.style.zIndex = 1;
    }
    div.onmouseout = function () {
        var streetId = $("#JS-show-content").attr("cmd-st-de");
        if(streetId !=null &&  streetId == that._object.id){
            div.style.backgroundImage="url("+basePath+"/static/global/images/circleHoverIcon.png)"
        }else {
            div.style.backgroundImage="url("+basePath+"/static/global/images/circleIcon.png)"
        }
        this.getElementsByTagName("span")[0].innerHTML = that._text;
    }

    mp.getPanes().labelPane.appendChild(div);
    div.setAttribute('hs-flag', that._object.id);
    return div;
}
CircleCustomOverlay.prototype.draw = function () {
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
    this._div.style.top = pixel.y - 30 + "px";
}


function IconOverlay(point, text, mouseoverText, funt, object) {
    this._point = point;
    this._text = text;
    this._overText = mouseoverText;
    this._funt = funt;
    this._obj = object;
}
IconOverlay.prototype = new BMap.Overlay();
IconOverlay.prototype.initialize = function (map) {
    this._map = map;
    var div = this._div = document.createElement("div");
    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
    div.style.overflow = "hidden";
    var that = this;
    var span = this._span = document.createElement("span");
    if((that._text+"").length>3){
        span.style.position = "relative";
        span.style.left = "-8px";
    }
    div.appendChild(span);
    //span.appendChild(document.createTextNode(this._text));
    span.innerHTML = this._text;

    var arrow = this._arrow = document.createElement("div");
    arrow.style.position = "absolute";
    arrow.style.left = "10px";
    arrow.style.overflow = "hidden";
    arrow.setAttribute("class", "u-icon-arrow");

    div.setAttribute("class", "u-icon-hs");
    div.appendChild(arrow);
    div.onclick = function () {
        that._funt(that._obj);
    }

    //content div
    var contentDiv = this.content_div = document.createElement("div");
    //contentDiv.style.backgroundImage =basePath+'/static/global/images/circleIcon.png';
    contentDiv.style.backgroundColor="red"
    contentDiv.style.color ='#fff';
    contentDiv.style.position = "absolute";
    contentDiv.style.left = "38px";
    contentDiv.style.top = "0px";
    contentDiv.style.height = "26px";
    contentDiv.style.lineHeight = "26px";
    contentDiv.style.padding = "0px 5px 0px 5px";

    contentDiv.innerHTML = that._overText;
    div.appendChild(contentDiv);
    div.onmouseover = function () {
        //if($("#JS-show-content").css('display')=="none"&&$("#JS-building-show-content").css('display')=="none") {
        contentDiv.innerHTML = that._overText;
        div.style.overflow = "visible";
        div.style.backgroundImage = "url(" + basePath + "/static/global/images/hoverIcon.png)";
        if (zIndexNumber != null) {
            if(circleOverply!=null){
                circleOverply.style.zIndex = zIndexNumber;
            }else {
                circleOverply.style.zIndex = -10000;
            }
        }
        zIndexNumber = div.style.zIndex ;
        circleOverply = div;
        div.style.zIndex = 1;
        //}
    };
    div.onclick = function () {
        if(blueCircleElement!=null){
            blueCircleElement.style.overflow = "hidden";
            blueCircleElement.style.backgroundImage = "url(" + basePath + "/static/global/images/locationIcon.png)";
            blueCircleElement = div;
        }

        that._funt(that._obj);
    }
    div.onmouseout = function () {
        if(div==blueCircleElement){
            return
        }
        this.getElementsByTagName("span")[0].innerHTML = that._text;
        arrow.style.backgroundPosition = "0px -30px";
        div.style.overflow = "hidden";
        div.style.backgroundImage = "url(" + basePath + "/static/global/images/locationIcon.png)";
    }

    mp.getPanes().labelPane.appendChild(div);
    if(that._obj!="undefined"){
        div.setAttribute("hs-flag", that._obj.id);
    }
    return div;
}
IconOverlay.prototype.draw = function () {
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
    this._div.style.top = pixel.y - 30 + "px";
}

// 复杂的自定义覆盖物
function RectangleCustomOverlay(point, text, mouseoverText, funt, object) {
    this._point = point;
    this._text = text;
    this._overText = mouseoverText;
    this._funt = funt;
    this._obj = object;
}
RectangleCustomOverlay.prototype = new BMap.Overlay();
RectangleCustomOverlay.prototype.initialize = function (map) {
    this._map = map;
    var div = this._div = document.createElement("div");
    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);

    var span = this._span = document.createElement("span");
    div.appendChild(span);
    //span.appendChild(document.createTextNode(this._text));
    span.innerHTML = this._text;
    var that = this;

    var arrow = this._arrow = document.createElement("div");
    arrow.style.position = "absolute";
    arrow.style.left = "10px";
    arrow.style.overflow = "hidden";
    arrow.setAttribute("class", "u-icon-arrow");

    div.setAttribute("class", "u-icon-hs");
    div.appendChild(arrow);
    div.onclick = function () {
        that._funt(that._obj);
    }
    div.onmouseover = function () {
        this.getElementsByTagName("span")[0].innerHTML = that._overText;
        arrow.style.backgroundPosition = "0px -20px";
    }
    div.onmouseout = function () {
        this.getElementsByTagName("span")[0].innerHTML = that._text;
        arrow.style.backgroundPosition = "0px -30px";
    }

    mp.getPanes().labelPane.appendChild(div);
    if(that._obj!="undefined"){
        div.setAttribute("hs-flag", that._obj.id);
    }
    return div;
}
RectangleCustomOverlay.prototype.draw = function () {
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
    this._div.style.top = pixel.y - 30 + "px";
}


// 企业覆盖物定义
function EnterpriseCustomOverlay(point, text, mouseoverText, funt, object) {
    this._point = point;
    this._text = text;
    this._overText = mouseoverText;
    this._funt = funt;
    this._obj = object;
}
EnterpriseCustomOverlay.prototype = new BMap.Overlay();
EnterpriseCustomOverlay.prototype.initialize = function (map) {
    this._map = map;
    var div = this._div = document.createElement("div");
    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
    div.setAttribute("class", "u-enterprise");
    var span = this._span = document.createElement("span");
    div.appendChild(span);
    span.style.display = "none";
    span.appendChild(document.createTextNode(this._text));
    var that = this;

    var arrow = this._arrow = document.createElement("div");
    arrow.style.position = "absolute";
    arrow.style.top = "22px";
    arrow.style.left = "10px";
    arrow.style.overflow = "hidden";
    div.appendChild(arrow);
    div.onclick = function () {
        if(that._obj!=null){
            that._funt(that._obj);
        }
    }
    var over_ele=null;
    div.onmouseover = function () {
        this.getElementsByTagName("span")[0].innerHTML = that._overText;
        arrow.style.backgroundPosition = "0px -20px";
        span.style.display = "inline";
        span.style.zIndex = 10;
        if(that._obj!=null){
            over_ele = getElementByAttr("div","hs-id",that._obj.id);
            if(over_ele!=null){
                over_ele[0].style.backgroundColor = "#666";
            }
        }

    }
    div.onmouseout = function () {
        this.getElementsByTagName("span")[0].innerHTML = that._text;
        arrow.style.backgroundPosition = "0px 0px";
        span.style.display = "none";
        if(over_ele!=null){
            over_ele[0].style = "none";
        }
    }
    mp.getPanes().labelPane.appendChild(div);
    return div;
}
EnterpriseCustomOverlay.prototype.draw = function () {
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
    this._div.style.top = pixel.y - 30 + "px";
}
// 地址变更页面变更地址图标
function ChangeAddressIcon(point, text, object,img) {
    this._point = point;
    this._text = text;
    this._obj = object;
    this._img = img;
}
ChangeAddressIcon.prototype = new BMap.Overlay();
ChangeAddressIcon.prototype.initialize = function (map) {
    this._map = map;
    var div = this._div = document.createElement("div");
    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
    div.style.backgroundImage = "url(" + basePath + "/static/global/images/" + this._img + ")";
    div.style.backgroundSize = "34px 53px";
    div.style.height = "55px";
    div.setAttribute("class", "u-enterprise");
    var span = this._span = document.createElement("span");
    div.appendChild(span);
    span.style.display = "none";
    span.appendChild(document.createTextNode(this._text));
    var that = this;

    var arrow = this._arrow = document.createElement("div");
    arrow.style.position = "absolute";
    arrow.style.top = "22px";
    arrow.style.left = "10px";
    arrow.style.overflow = "hidden";
    div.appendChild(arrow);
    div.onclick = function () {
        if(that._obj!=null){
            that._funt(that._obj);
        }
    }
    var over_ele=null;
    div.onmouseover = function () {
        this.getElementsByTagName("span")[0].innerHTML = that._overText;
        arrow.style.backgroundPosition = "0px -20px";
        span.style.backgroundColor = "#f34538";
        span.style.display = "inline";
        span.style.zIndex = 10;
        span.innerHTML=that._text;
        span.style.color = "black";
    }
    div.onmouseout = function () {
        this.getElementsByTagName("span")[0].innerHTML = that._text;
        arrow.style.backgroundPosition = "0px 0px";
        span.style.display = "none";
        if(over_ele!=null){
            over_ele[0].style = "none";
        }
    }
    mp.getPanes().labelPane.appendChild(div);
    return div;
}
ChangeAddressIcon.prototype.draw = function () {
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
    this._div.style.top = pixel.y - 30 + "px";
}

$(function () {
    $(".u-sch-item-sel").hide();
    $(".u-sch-item").hover(function () {
        var _ = $(this);
        _.find(".u-sch-item-sel").show();
    }, function () {
        var _ = $(this);
        _.find(".u-sch-item-sel").hide();
    });
});
//工具类
var HSUtil = {
    dataBind: function (expr, data) {//数据绑定
        if (!expr) {
            expr = 'body';
        }
        var $elements = jQuery(expr).find("[hs-model]");
        $elements.each(function () {
            var that = $(this);
            var name = that.attr("hs-model");
            if (that.is('input') || that.is('select')) {
                that.val(data[name]);
            } else {
                that.html(data[name]);
            }
        });
    }
};

//企业对象
function Enterprise(optins,obj,funt) {
    this._id = optins.id;
    this._name = optins.name;
    this._business = optins.majorBusiness;
    this._address = optins.address;
    this._obj = obj;
    this._funt = funt;
}
Enterprise.prototype = {};
Enterprise.prototype.createDiv = function(i){
    var div = this._div = document.createElement("div");
    var that = this;
    div.onclick = function () {
        that._funt(that._obj);
    };
    div.setAttribute("class", "u-item");
    div.setAttribute("hs-id", that._obj.id);
    var divui1 = document.createElement("div");
    divui1.setAttribute("class", "u-item-1");
    var divui2 = document.createElement("div");
    divui2.setAttribute("class", "u-item-2");
    var divui3 = document.createElement("div");
    divui3.setAttribute("class", "u-item-2");
    if(that._business==undefined){
        that._business = "暂无";
    }
    if(that._address==undefined){
        that._address = "暂无";
    }
    if(that._name==undefined){
        that._name = "暂无";
    }
    divui1.innerHTML = '<span>'+i+'、</span>'+that._name;
    divui2.innerHTML = '<span title="'+that._business+'" class="u-eps">主营业务：'+that._business+'</span>';
    divui3.innerHTML = '<span title="'+that._address+'" class="u-eps">地址：'+that._address+'</span>';
    div.appendChild(divui1);
    div.appendChild(divui2);
    div.appendChild(divui3);
    return div;
}


//企业对象2
function Enterprise2(optins,obj,funt) {
    this._id = optins.id;
    this._name = optins.name;
    this._business = optins.majorBusiness;
    this._address = optins.address;
    this._obj = obj;
    this._funt = funt;
}
Enterprise2.prototype = {};
Enterprise2.prototype.createDiv = function(i){
    var div = this._div = document.createElement("div");
    var that = this;
    div.onclick = function () {
        that._funt(that._obj);
    };
    div.setAttribute("class", "u-item");
    var divui1 = document.createElement("div");
    divui1.setAttribute("class", "u-item-1");
    var divui2 = document.createElement("div");
    divui2.setAttribute("class", "u-item-2");
    var divui3 = document.createElement("div");
    divui3.setAttribute("class", "u-item-2");
    if(that._business==undefined){
        that._business = "暂无";
    }
    if(that._address==undefined){
        that._address = "暂无";
    }
    if(that._name==undefined){
        that._name = "暂无";
    }
    var title = '<sapn>'+i+'、</sapn>'+that._name;
    if(that._obj.addressChange==1){
        title += '<nobr style="font-size: 9px">  地址变更</nobr>';
    }
    if(that._obj.majorBussinessChange==1){
        title += '<nobr style="font-size: 9px">  业务变更</nobr>';
    }
    divui1.innerHTML = title+'<nobr><span class="addressChangeInfo">地址变更</span></nobr>';
    divui2.innerHTML = '<sapn title="'+that._business+'" class="u-eps">'+"主营业务："+that._business+'</></sapn>';
    divui3.innerHTML = '<sapn title="'+that._address+'" class="u-eps">地址：'+that._address+'</sapn>'
    div.appendChild(divui1);
    div.appendChild(divui2);
    div.appendChild(divui3);
    return div;
}



//建筑对象
function Building(optins,obj,funt) {
    this._id = optins.id;
    this._name = optins.name;
    this._entepriseNumber = optins.enterpriseCounts+"家";
    this._address = optins.address;
    this._employeeNumber=optins.employeeCounts==0?"暂无":optins.employeeCounts+"人";
    this._obj = obj;
    this._funt = funt;
}
Building.prototype = {};
Building.prototype.createBuildDiv = function (i) {
    var that = this;
    var tmpl = [
        '<div class="u-item">',
        '<div class="u-item-1">',
        '<span>'+i+'、</span><span hs-model="_name">1</span>',
        '</div>',
        '<div class="u-item-2">',
        '<span>楼宇内企业：</span><span hs-model="_entepriseNumber">1</span>',
        '</div>',
        '<div class="u-item-2">',
        '<span>地址：</span><span class="u-eps" hs-model="_address">1</span>',
        '</div>',
        '<div class="u-item-2">',
        '<span>从业人数：</span><span hs-model="_employeeNumber">1</span>',
        '</div>',
        '</div>'
    ].join("");
    var rst = $(tmpl);
    HSUtil.dataBind(rst, that);
    rst[0].onclick = function(){
        that._funt(that._obj);
    }
    return rst[0];
};

//tab切换
$(function () {
    function showMyTab(){
        var that = $(this);
        var showId = that.attr("hs-show");
        //change title
        $(".u-tabs").find(".u-tab.active").removeClass("active");
        //hide content
        $(".JS-hs-tab-content").hide();
        $("#"+showId).show();
        that.addClass("active");
        if(that.has("[hs-call]")){
            var callback = that.attr("hs-call");
            var options = that.attr("hs-options");
            callback&&window[callback](options);
        }
    }
    $("[hs-show]").click(showMyTab);
});
//初始化激活nav
$(function () {
    var identity = $("body").data("identity");
    function selectNav(){
        var that = $(this);
        if(that.attr("hs-active")==identity){
            that.addClass("active");
        }
    }
    $("[hs-active]").each(selectNav);
});