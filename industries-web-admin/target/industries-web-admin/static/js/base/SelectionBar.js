$(function () {
        var pathName=window.location.pathname;
        var pathArray=pathName.split("/");
        var menuArray=new Array();
        for(var i=0;i<pathArray.length;i++){
            if(""!= $.trim(pathArray[i])&&pathArray[i].indexOf(".")<0){
                menuArray.push(pathArray[i]);
            }
        }
        var isFirst=true;
        var linkHtml='<a href="<%=path_bottom%>/index.html">首页</a>';
        for(var i=0;i<menuArray.length;i++) {
            if($("#"+menuArray[i])){
                $("#"+menuArray[i]+">.dropdown-toggle").click();
                /*var text= $("#"+menuArray[i]+">.dropdown-toggle").text().trim();
                var linkHtml= '<a href="javasript:void(0)">'+text+'</a>';
                if(isFirst){
                    isFirst=false;
                    linkHtml='<i class="icon-home home-icon"></i>'+linkHtml;
                }
                $(".breadcrumb").append($("<li>").html(linkHtml));*/
            }
        }
        /*if(isFirst){
            isFirst=false;
            linkHtml='<i class="icon-home home-icon"></i>'+linkHtml;
            $(".breadcrumb").append($("<li>").html(linkHtml));
        }*/
        /* var lastLink=$('a[href="'+pathName+'"]');
        var lastText="";
        if(lastLink){
            lastText=lastLink.text().trim();
            if(lastText){
                $('title').text(lastText);
            }else{
                lastText="";
                $('title').text('');
            }
        }
        $(".breadcrumb").append($("<li>").attr("class","active").html(lastText)); */
    });
