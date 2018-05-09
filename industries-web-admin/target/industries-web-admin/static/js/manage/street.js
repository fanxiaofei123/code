$(document).ready(function(){
    initSelect("/admin/street/selectAll.do");
});
/*页面加载完毕后得到所有街道*/
function initSelect(url){
    var str = "";
    var streetName;
    /*街道名称*/
    $.get("/admin/street/selectAll.do",function(data){
        var data =  jQuery.parseJSON(data);
        if(data.status == 1000){
            for(var i = 0;i<data.entities.length;i++){
                var o=data.entities[i];
                streetName = o.name;
                //if($("#addEnStreet").val()== streetName){
                //    str += "<option value="+ o.id+" selected='true'>"+ o.name+"</option>"
                //    $("#addEnStreet").attr("disabled",true);
                //    $("#addEnStreet").css("background-color","#E8E8E8");
                //}
                //else{
                //    str += "<option value="+ o.id+">"+ o.name+"</option>"
                //}
            }
        }
    })
}

/*下拉框街道鼠标选中时改变对应的社区*/
//function myEnStreet(){
//    var list = initSelect("/admin/community/selectAll.do");
//    console.log(list);
//    //var streetId = $("#addEnStreet").val();
//    //console.log(streetId);
//    //
//    //$.ajax({
//    //	type: "post",
//    //	url: basePath+"/building/queryByStreetId.do",
//    //	data: {"id":streetId},
//    //	dataType: "json",
//    //	success: function (data) {
//    //		$("#addEnCommunity").find("option").remove();
//    //		var str="";
//    //		for(var i = 0;i<data.length;i++){
//    //			var o=data[i];
//    //			str += "<option value="+ o.communityId+">"+ o.communityName+"</option>"
//    //			if(o.communityName == communityName){
//    //				str += "<option value="+ o.communityId+" selected='selected'>"+ o.communityName+"</option>"
//    //			}
//    //			$("#addCommunity").html(str);
//    //		}
//    //	},
//    //	error: function () {
//    //	}
//    //});
//}


