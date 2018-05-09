$(function () {
    window.onresize=function(){ location=location };

    var loadingContent = "<div style='line-height: 600px;text-align: center;'>图表数据计算中，请稍后<img src='" + basePath + "/static/images/loading.gif'/></div>";
    var indusName = "农、林、牧、渔业";

    // 行业数据
    var firstIndustryIdAndName = getIndustry(1);
    var secondIndustryIdAndName = getIndustry(2);
    var thirdIndustryIdAndName = getIndustry(3);

    var oneContent = $("#one-contents");
    var twoContent = $("#two-contents");
    var threeContent = $("#three-contents");

    // var oneContentString = '<li class="industryClick active"><a role="tab" id="1"  data-toggle="tab">全部</a></li>';
    // var twoContentString = '<li class="industryClick"><a role="tab" id="2"  data-toggle="tab">全部</a></li>';
    // var threeContentString = '<li class="industryClick"><a role="tab" id="3"  data-toggle="tab">全部</a></li>';

    // for(var i=0;i<firstIndustryIdAndName.data.length;i++){
    //     oneContentString += '<li class="industryClick"><a role="tab" id="'+firstIndustryIdAndName.data[i].id+'" data-toggle="tab">'+firstIndustryIdAndName.data[i].name+'</a></li>';
    // }
    // for(var x=0;x<secondIndustryIdAndName.data.length;x++){
    //     twoContentString += '<li class="industryClick"><a role="tab" id="'+secondIndustryIdAndName.data[x].id+'" data-toggle="tab">'+secondIndustryIdAndName.data[x].name+'</a></li>';
    // }
    // for(var y=0;y<thirdIndustryIdAndName.data.length;y++){
    //     threeContentString += '<li class="industryClick"><a role="tab" id="'+thirdIndustryIdAndName.data[y].id+'" data-toggle="tab">'+thirdIndustryIdAndName.data[y].name+'</a></li>';
    // }
    var oneContentString = '<li class="industryClick active"><a data-toggle="tab">全部</a></li>';
    var twoContentString = '<li class="industryClick"><a data-toggle="tab">全部</a></li>';
    var threeContentString = '<li class="industryClick"><a data-toggle="tab">全部</a></li>';

    for(var i=0;i<firstIndustryIdAndName.data.length;i++){
        oneContentString += '<li class="industryClick"><a id="'+firstIndustryIdAndName.data[i].id+'" data-toggle="tab">'+firstIndustryIdAndName.data[i].name+'</a></li>';
    }
    for(var x=0;x<secondIndustryIdAndName.data.length;x++){
        twoContentString += '<li class="industryClick"><a id="'+secondIndustryIdAndName.data[x].id+'" data-toggle="tab">'+secondIndustryIdAndName.data[x].name+'</a></li>';
    }
    for(var y=0;y<thirdIndustryIdAndName.data.length;y++){
        threeContentString += '<li class="industryClick"><a id="'+thirdIndustryIdAndName.data[y].id+'" data-toggle="tab">'+thirdIndustryIdAndName.data[y].name+'</a></li>';
    }

    oneContent.html(oneContentString);
    twoContent.html(twoContentString);
    threeContent.html(threeContentString);

    // 点击事件
    $(".industryClick").click(function () {
        $("#content").html(loadingContent);
        var tradeId = $(this).children().attr("id");
        var industryName = $(this).parent().attr("id");

        indusName = $(this).text();
        var industry ;
        if(indusName == "全部"){
            industry = $(this).parent().parent().attr("id");
        }
        if(industry == "one-industry"){
            indusName = "第一产业";
        }else if(industry == "second-industry"){
            indusName = "第二产业";
        }else if(industry == "three-industry"){
            indusName = "第三产业";
        }
        if(industryName == "one-contents"){
            if(tradeId=="1"){
                industryTrendChart("bar", "1");
            }else {
                industryTrendChart("bar", "1",tradeId);
            }
        }else if (industryName == "two-contents"){
            if(tradeId=="2"){
                industryTrendChart("bar", "2");
            }else {
                industryTrendChart("bar", "2",tradeId);
            }
        }else if (industryName == "three-contents"){
            if(tradeId=="3"){
                industryTrendChart("bar", "3");
            }else {
                industryTrendChart("bar", "3",tradeId);
            }
        }
    });

    $("#content").html(loadingContent);
    industryTrendChart("bar", "1");

    // 通用标题选中
    $("li[hs-active='change']").find("a").css("color", "white").css("background-color", "#3287AC");

    // 产业趋势图
    function industryTrendChart(chartType, industryId, tradeId, enterpriseId, startTime, endTime) {
        if (chartType) {
            chartType = "bar";
        }
        $.ajax({
            type: "GET",
            url: basePath + "/aIndexChart/getIndexData.do",
            data: {
                "chartType": chartType,
                "industryId": industryId,
                "tradeId": tradeId,
                "enterpriseId": enterpriseId,
                "startTime": startTime,
                "endTime": endTime
            },
            success: function (data) {
                if(isEmptyObject(data.datas)){
                    $("#content").html("<div style='line-height: 600px;text-align: center;'>数据库中<span style='color: red'>“"+indusName+"”</span>下没有企业数据！请选择其他行业！</div>");
                    return;
                }
                if (data.status == 200) {
                    barOrLineChar("content", data, "line");
                } else {
                    $("#content").html(data.msg);
                }
            }
        });
    }

    // 向后台请求行业数据
    function getIndustry(id) {
        var datas = null;
        $.ajax({
            type: "GET",
            async: false,
            url: basePath + "/category/getByIndustryId.do",
            data: {
                "id": id
            },
            success: function (data) {
                datas = data;
            }
        });
        return datas;
    }

    // 展示更多数据
    var hiddenArray  = [];
    $(".more-describe").click(function () {
        var thisSpan = $(this);
        $.each(hiddenArray,function(index,value){
            value.removeClass("hidden");
            value.next().addClass("hidden");
            hiddenArray.splice(index);
        });
        thisSpan.addClass("hidden");
        thisSpan.next().removeClass();
        hiddenArray.push(thisSpan);
    });

    // 判断对象是否为空
    function isEmptyObject(obj) {
        for (var key in obj) {
            return false;
        }
        return true;
    }
});
