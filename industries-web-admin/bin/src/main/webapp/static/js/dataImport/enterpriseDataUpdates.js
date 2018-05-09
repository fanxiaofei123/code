/**
 * Created by Administrator on 2016/7/12 0012.
 */
var file = null;//选中的文件

$(function () {
    document.getElementById("uploadButton").addEventListener('click', function () {
        $("#messageSpan").html("上传中...");
        var formData = new FormData();
        formData.append("file", file);
        if (file==null){
            $("#messageSpan").html("请选择上传文件");
            $("#messageSpan").css("color","red");
            return;
        }
        // 实例化一个AJAX对象
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState==4) {
                var obj = xhr.response;
                var objJson = eval('(' + obj + ')');
                if(objJson.status==800){
                    $("#messageSpan").html("不支持："+objJson.message+"格式");
                    $("#messageSpan").css("color","red");
                    return;
                }
                if(objJson.message=="600"){
                    $("#messageSpan").html("非法数据,请参考数据格式示例。");
                    return;
                }
                if(objJson.message=="700"){
                    $("#messageSpan").html("表头格式不正确。");
                    return;
                }
                var st= objJson.message.split(',');
                var failCount=st[1].split(':')[1];
                var message = "<span>上传成功" + st[0].split(':')[1] + "条,失败" + failCount + "条</span>";
                if(failCount!="0" && failCount!=null){
                    message += ",点击<a href='" + basePath + "/enterpriseValidata/downloadFailData.do'>下载失败数据</a>";
                }
                //$("#valiAndUploadDiv").insertBefore(message,$("#uploadButton").nextSibling);
                $("#messageSpan").html(message);
                loadLogs();
            }
        }
        xhr.open("POST", basePath+'/enterpriseValidata/validataButton.do', true);

        // 发送表单数据
        xhr.send(formData);

    });
    document.getElementById("uploadFile").addEventListener('change', function () {
        file = this.files[0];
    });


    //加载日志
    loadLogs();


});

function dateFormat(data){
    var d = new Date(data);
    Y = d.getFullYear() + '-';
    M = (d.getMonth()+1 < 10 ? '0'+(d.getMonth()+1) : d.getMonth()+1) + '-';
    D = d.getDate() + ' ';
    h = d.getHours() + ':';
    m = d.getMinutes() + ':';
    s = d.getSeconds();
    return Y + M + D + h + m + s;
}

function loadLogs(){
    $.ajax({
        url: basePath + "/enterpriseValidata/listLogs.do",
        type: "get",
        success: function (obj) {
            var dataList = obj.data;
            var str = "";
            for(var j=0;j<(dataList.length/6)+1;j++){
                str += "<tr>";
                if((j + 1) * 6>dataList.length){
                    var maxJ = dataList.length;
                }else {
                    var maxJ = (j + 1) * 6;
                }
                for(var i=(j*6);i<maxJ;i++){
                    var tmp = dataList[i];
                    var aLabel = "";
                    if(tmp.failCount!="0"){
                        aLabel="，点击<a href='" + basePath + "/enterpriseValidata/downloadFailDataForLog.do?logId=" + tmp.id + "'>下载失败数据</a>";
                    }
                    str+="<td onclick='showAndHiddenA(this)'><span style='font-size: 15px;padding-left: 30px'>"+dateFormat(tmp.date)+"</span><br>" +
                        "<span style='visibility: hidden;'>上传成功"+tmp.successCount+"条<br>上传失败"+tmp.failCount+"条"+aLabel+"</span></td>"
                }
                str += "</tr>";
            }
            $("#tb").html(str);
        }
    });

}

function showAndHiddenA(ele){
    var cArray = $(ele).children();
    var $span = $(cArray[2]);
    if($span.css("visibility")=="hidden") {
        $span.css("visibility", "visible");
    }else {
        $span.css("visibility", "hidden");
    }
}