//发送AJAX同时响应页面做展示   不同页面显示不同内容


var saveOrCancel = 0;
	$(function(){
		show(basePath+"/admin/street/listByModel.do");
		//ajax提交表单
		closeModel();

		//验证提交表单
		$("#ffAdd").validate({
			submitHandler:formSubmit,
			rules: getRules(),
			messages:validateMessages()
		});
	});
	function show(url,parameter){
		showLoading("加载中....");
		$.get(url,parameter,function(data){
			var str ="";
			var infolist = jQuery.parseJSON(data);
			for(var i = 0;i<infolist.entities.length;i++){
				var o=infolist.entities[i];
				if((i+1) == 1){
					str += "<tr>" + "<td class='right' colspan='2'>"+"<span style='display: none'>"+ o.id+"</span>" + "<span class='date-s'>"+o.name+"</span>"+"<span class='btn-u-d'>&nbsp;&nbsp;"+"<a href='javascript:void(0)' class='btn-u-r'   cmd-flag='1'>重命名</a>"+"&nbsp;&nbsp;<a href='javascript:void(0)'  class='btn-u-d'   cmd-flag='2' >删除</a>"+"</span>"+ "</td>";
				}else if((i+1) == infolist.entities.length){
					str += "<td class='right' colspan='2'>"+"<span style='display: none'>"+ o.id+"</span>" + "<span class='date-s'  >"+o.name+"</span>" +"<span class='btn-u-d' >&nbsp;&nbsp;"+"<a href='javascript:void(0)'  class='btn-u-r'    cmd-flag='1'  >重命名</a>"+"&nbsp;&nbsp;<a href='javascript:void(0)'  class='btn-u-d'  cmd-flag='2'  >删除</a>"+"</span>"+ "</td>" + "</tr>";
				}
				else if((i+1)%5 == 0){
					str += "<td class='right' colspan='2'>"+"<span style='display: none'>"+ o.id+"</span>" + "<span  class='date-s' >"+o.name+"</span>"+ "<span class='btn-u-d' >&nbsp;&nbsp;"+"<a href='javascript:void(0)'  class='btn-u-r'   cmd-flag='1'  >重命名</a>"+"&nbsp;&nbsp;<a href='javascript:void(0)' cmd-flag='2' class='btn-u-d'    >删除</a>"+"</span>"+"</td>"+"</tr><tr>";

				}else{
					str += "<td class='right' colspan='2'>"+"<span style='display: none'>"+ o.id+"</span>" +"<span  class='date-s' >"+o.name+"</span>"+"<span class='btn-u-d' >&nbsp;&nbsp;"+"<a href='javascript:void(0)' class='btn-u-r'    cmd-flag='1'  >重命名</a>"+"&nbsp;&nbsp;<a href='javascript:void(0)' cmd-flag='2' class='btn-u-d'    >删除</a>"+"</span>"+"</td>";
				}
			}

			$("#tbody").html(str);
			hideLoading();

			var communityFlg = $("#st-navi").attr('community-flg');
			closeModel();

			renameOrDelete();
			if(communityFlg != 1){
				streetClick();
			}

		});
		$('#streetFrom').on('hidden.bs.modal', function () {
			if(saveOrCancel == 2){
				var isSave = confirm("数据发生改变是否保存？");
				if(isSave){
					updateOrAdd();
				}
			}


		});
	}

	function streetClick(){

		$(".date-s").on('click', function () {
			var that = $(this);
			var id = that.closest('td').find('span')[0].innerHTML;
			streetId = that.closest('td').find('span')[0].innerHTML;
			var parameter = {'id':id};
			show(basePath+"/admin/street/findCommunity.do",parameter);
			changeHead(this);
		});
	}
/**
 * 跳转改变页面
 * @param that
 */
	function  changeHead(that){
		var navi = $("#st-navi").find('small');
		var that = $(that);
		$(navi[0]).find('span').html("<span onclick='window.location.reload()' style='cursor: pointer'>返回街道列表</span>");
		$("#st-navi").attr('community-flg','1');
		navi[1].innerHTML = that.html();
		$("#st-navi").find('a').html('+新增社区');
	}
	function renameOrDelete(){
		$(".btn-u-r,.btn-u-d").on('click', function () {
			var that = $(this);
			var flag = that.attr('cmd-flag');
			if(flag != undefined){
				var id = that.closest('td').find('span')[0].innerHTML;
				$("#objId").val(id);
			}
			if(flag !=undefined && flag == 1){
				var modal = $('#streetFrom');
				var text = that.closest('td').find('span')[1].innerHTML;
				$("#lblAddTitle").html("修改");
				$("#name").val(text);
				$("#id").val(id);
				modal.modal('show');
				saveOrCancel = 2;
			}else if(flag !=undefined && flag == 2){
				deleteDate();
			}
		});
	}
	function deleteDate(){
		var communityFlg = $("#st-navi").attr('community-flg');
		var id = $("#objId").val();
		if(communityFlg == 1){
			var url =basePath+ '/admin/community/delete.do';
			var reLoadUrl = "/admin/street/findCommunity.do";
				var streetParm = {'id':streetId};
				del(url, id, reLoadUrl, streetParm);

		}else{
				var url = basePath+'/admin/street/delete.do';
				var reloadUrl = "/admin/street/listByModel.do"
				del(url, id, reloadUrl);
		}
	}
/**
 * 删除
 * @param url
 * @param id
 * @param reloadUrl
 * @param reLoadParm
 */
function del(url,id,reloadUrl,reLoadParm){

		var txt=  "是否删除选中项？";
		var option = {
			title: "提示",
			icon: "-48px 0",
			btn: parseInt("0011",2),
			onOk: function(){

				saveOrCancel = 1;
				$.get(url+"?id="+id,function(data){
					var data = jQuery.parseJSON(data);
					console.log(data);
					if(data.status == 1000){
						if(reLoadParm !=undefined){
							show(basePath+reloadUrl,reLoadParm);
							return;
						}
						show(basePath+reloadUrl);
					}
					if(data.status == 1001){
						var msg = data.msg;
						window.wxc.xcConfirm(msg, "custom");
					}
				});
			}
		}
//    	 给出参数
	window.wxc.xcConfirm(txt, "custom", option);
}
/*
	街道添加框
 */
	function  addModel(){
		reset();
		var communityFlg = $("#st-navi").attr('community-flg');
		$("#lblAddTitle").html("添加");
		$(".add-s").show();
		if(communityFlg  == 1){
			var str ="";
			var url = basePath+ '/admin/street/selectAll.do';
			/*$("#add-street").show();*/
			$.get(url,function(data){
				var data =   jQuery.parseJSON(data);
				if(data.status == 1000){
					for(var i = 0;i<data.entities.length;i++){
						var o=data.entities[i];
						if(streetId == o.id){
							str += "<option selected='true' value="+ o.id+">"+ o.name+"</option>"
						}else{
							str += "<option  value="+ o.id+">"+ o.name+"</option>"
						}
					}
				}

				$("#stId").html(str);
			});
		}
		$('#streetFrom').modal('show');
		saveOrCancel = 2;
	}
/**
 * 更新或添加
 */
	function updateOrAdd(){
		var name = $("#name").val();
		var streetUrl = '/admin/street/add.do';
		var communityUrl = '/admin/community/add.do';
		var communityFlg = $("#st-navi").attr('community-flg');
		if(communityFlg == 1){
				formSubmit(communityUrl)
		}else{
				formSubmit(streetUrl);
		}
	}
/**
 * 提交表单
 */
function formSubmit(url){
	saveOrCancel = 1;
	if($("#ffAdd").valid()) {
		$('#ffAdd').ajaxSubmit({
			type: "post",
			dataType: "json",
			url: basePath + url,
			success: function (data) {
				if (data != null && data.status == "1000") {  //添加社区成功
					refresh();
				} else {
					window.wxc.xcConfirm(data.msg, window.wxc.xcConfirm.typeEnum.error);
				}
			}
		});
		return true;
	}
}

function  refresh(){
	var communityFlg = $("#st-navi").attr('community-flg');
	if(communityFlg == 1){
		var showp = {'id':streetId};
		show(basePath + "/admin/street/findCommunity.do", showp);
	}else{
		show(basePath+"/admin/street/listByModel.do");
	}
 }


function  closeModel(){
	saveOrCancel = 0;
/*
	$("#add-street").css('display','none');
*/
	$('#streetFrom').modal('hide');
	reset();
}

function reset(){
	$("#id").val('');
	$("#name").attr('data-id','')
	$('#name').val('');
	$("#st-navi").attr('')
	$('#stId').html('');
}