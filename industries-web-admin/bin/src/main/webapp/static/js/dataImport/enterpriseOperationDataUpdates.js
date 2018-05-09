var file = null;//选中的文件

$(function () {
    document.getElementById("uploadButton").addEventListener('click', function () {
        document.getElementById("uploadButton").disabled=true;
        $("#messageSpan").html("<h4 class='text-warning'>上传中,请耐心等待<img src='"+basePath+"/static/images/loading.gif'/></h4><hr>");
        var formData = new FormData();
        formData.append("file", file);
        if (file==null){
            $("#messageSpan").html("<h4 class='text-warning'>请选择上传文件</h4><hr>");
            $("#messageSpan").css("color","red");
            return;
        }
        // 实例化一个AJAX对象
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState==4) {
                document.getElementById("uploadButton").disabled=false;
                var obj = xhr.response;
                var objJson = eval('(' + obj + ')');
                if(objJson.status==800){
                    $("#messageSpan").html("<h4 class='text-warning'>不支持："+objJson.message+"格式</h4><hr>");
                    $("#messageSpan").css("color","red");
                    return;
                }
                if(objJson.message=="600"){
                    $("#messageSpan").html("<h4 class='text-warning'>非法数据,请参考数据格式示例。</h4><hr>");
                    return;
                }
                if(objJson.message=="700"){
                    $("#messageSpan").html("<h4 class='text-warning'>表头格式不正确。</h4><hr>");
                    return;
                }
                if(objJson.status==500){
                    $("#messageSpan").html(objJson.message);
                    return;
                }
                var st= objJson.message.split(';');
                var message = "<h4 class='text-warning'>消息：</h4>";
                if(st[0] != "null"){
                    message += "<h4 style='color: red'>“" + st[1]+"”企业处保存错误!</h4><h4 style='color: red'>" + st[0]+"</h4>";
                }
                message += "<h4 class='text-warning'>上传成功" + st[2]+"家企业数据（共" + st[3] + "条数据）！</h4><hr>";
                $("#messageSpan").html(message);
            }
        };
        xhr.open("POST", basePath+'/enterpriseValidata/enterpriseOperationData.do', true);
        // 发送表单数据
        xhr.send(formData);

    });
    document.getElementById("uploadFile").addEventListener('change', function () {
        file = this.files[0];
    });
});