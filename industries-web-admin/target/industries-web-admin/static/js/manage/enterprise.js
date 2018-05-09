//发送AJAX同时响应页面做展示   不同页面显示不同内容
$(function(){
	changeFlag = false;
	$("#addEnIndustry,#addTrade,#addCategory,#employeeCount,#phoneNumber,#registerTime,#registerCapital,#openFlag,#address,#addEnStreet,#addEnCommunity,#majorBusiness").change(function() {
		changeFlag = true;
	});
	$(".nav-list > li").click(function() {
		if(changeFlag) {
		    var flag = confirm("是否保存当前页面修改？");
			if(flag) {
				formSubmit();
			}else {
				return;
			}
		}
	});
	//验证
	$.validator.setDefaults( {
        submitHandler: function () {
            $("#updateEnForm").submit();
        }
    } );

	var updateFalg = $("#updateFalg");
	if(updateFalg !=undefined  && updateFalg.val()==1){
		enterpriseEditClick();
	}

	jQuery.validator.addMethod("mobileTelephone", function(value, element) {
		var mobile = /^(((1[0-9]{2})|(1[0-9]{2}))+\d{8})$/;
		var tel = /^((0[0-9]{2})+\d{7,8})$/;
		return this.optional(element) || (tel.test(value) || mobile.test(value));
	}, "请正确填写您的联系电话");

    // var cmccMobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    //     var tel = /^\d{3,4}?\d{7,8}$/;
		// //7，8位座机号码
    //     var tel1 = /\d{7,8}/;
    //     if(tel1.test(value) ||tel.test(value) || (cmccMobile.test(value) || (value.length == 11 && cmccMobile.test(value)))){
		// 	$("#btnUpdate").removeAttr("disabled");
		// }else{
		// 	$("#btnUpdate").attr("disabled","disabled");
		// }
    //     return this.optional(element) || tel1.test(value) ||tel.test(value) || (value.length == 11 && cmccMobile.test(value));
    // }, "电话格式有误，请输入座机或手机号");

	/**校验年份格式 支持 数字 */
	  jQuery.validator.addMethod("isYears", function(value, element){
		  var thedate=/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
		  /*var thedate2=/^((?:19|20)\d\d)年([1-9]|1[012])月([1-9]|[12][0-9]|3[01])日$/;*/

		  return this.optional(element) || thedate.test(value);
	  }, "格式输入有误，仅支持以下格式：2016/06/01，请重新输入");

	/**校验总数/金额格式 支持 数字 */
	jQuery.validator.addMethod("isNumber", function(value, element){
		var thenum=/^([0-9]+)$/;
		if(thenum.test(value)){
			$("#btnUpdate").removeAttr("disabled");
		}else{
			$("#btnUpdate").attr("disabled","disabled");
		}
		return thenum.test(value);
	}, "请输入0或正数");

	$("#updateEnForm").validate({
        rules: {
        	address: {
                required: true,
                maxlength:200
            },
            majorBusiness:{
            	required: true,
                maxlength:300
            },
            employeeCount:{
            	required: false,
                maxlength:20,
				isNumber:true
            },
            phoneNumber:{
            	required: false,
                mobileTelephone:true
            },
            registerTime:{
            	required: false,
                isYears:true
            },
            registerCapital:{
            	required: false,
                maxlength:30,
				isNumber:true
            },
            openFlag:{
            	required: false,
                maxlength:20
            }
        },
        messages: {
        	address: {
                required: "请输入地址",
                maxlength: "地址输入有误，请重新输入"
            },
            majorBusiness: {
                required: "请输入主营业务",
                maxlength: "营业务输入有误，请重新输入"
            },
            employeeCount: {
                required: "请输入从业人数",
                maxlength: "从业人数输入有误，请重新输入",
				isNumber:"必须输入整数"
            },
            phoneNumber: {
                required: "请输入电话号码",
                mobileTelephone: "联系电话输入有误，请重新输入!"
            },
            registerTime: {
                required: "请输入日期",
                isYears: "格式输入有误，仅支持以下格式：2016/06/01，请重新输入"
            },
            registerCapital: {
                required: "请输入注册资金",
                maxlength: "注册资金输入有误，请重新输入",
				isNumber:"必须输入整数"
            },
            openFlag: {
                required: "请输入状态",
                maxlength: "状态输入有误，请重新输入"
            }
        },
        errorElement: "em",
        errorPlacement: function ( error, element ) {
            error.addClass( "help-block").css({"color":"red"});
            if ( element.prop( "type" ) === "checkbox" ) {
                error.insertAfter( element.parent( "label" ) );
            } else {
                error.insertAfter( element );
            }
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
        },
        unhighlight: function (element, errorClass, validClass) {
            $( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
        }
    });
	
	
	
	show(basePath+"/enterprise/aEnterprise/listByModel.do");

	$("button[name=close]").click(function () {
		window.location.reload();
	});

});
//sign
function formSubmit(){
	var url = basePath + "/enterprise/updateByPrimaryKeySelective.do";
	$('#updateEnForm').ajaxSubmit({
		type: "post",
		dataType: "json",
		url:url,
		success: function (data) {
			if (data.code==200){
				alert("更新成功");
				var objId = data.data.enterpriseId;
				var url = basePath + "/enterprise/selectByPrimaryKey.do";
				window.location.href = url + "?id=" + objId;
			}
		},error:function(){
			alert("更新失败");
		}
	});
}


function show(url){
	//showLoading("数据正在加载中...");
	$.get(url,function(data){
		var str ="";
		var infolist = jQuery.parseJSON(data);
		page = infolist.page;
		if (infolist.entities.length!=0){
			if(page.currentPage != 1){
				var countSort = (page.currentPage-1)*10;
			}
			else{
				var countSort = 0;
			}
			var newCountSort = parseInt(countSort);
		for(var i = 0;i<infolist.entities.length;i++){
			var o=infolist.entities[i];
			newCountSort += 1;
			str+="<tr>"+
				"<td class='center'>"+newCountSort+"</td>"+
				"<input type='hidden' id='enterpriseId' class='ace' str-id="+o.streetId+" com-id="+o.communityId+" name='subBox' value=\""+o.id+"\"/>"+
				"<td class='center'><a style='color: #563BF5' href="+basePath+"/enterprise/selectByPrimaryKey.do?id="+o.id+">"+changeValue(o.registerNumber)+"</a></td>"+
				"<td class='center eName'>"+changeValue(o.name)+"</td>"+
				"<td class='center'>"+changeValue(o.registerAddress)+"</td>"+
				"<td class='center'><a style='cursor: pointer' href="+basePath+"/enterprise/selectByPrimaryKey.do?id="+o.id+"&flag=1"+">编辑</a></td>" +
				"<td class='center'><a style='cursor: pointer' onclick='delEnterprise("+o.id+")'>删除</a></td>"+
				"</tr>";
		}
			//lzs 对象未取到 报错
			//document.getElementById('hiddenClass').style.display = "none";
			//document.getElementById('pageClass').style.display = "block";
		$("#tbody").html(str);

		//设定page的属性
		setPage(infolist);
		}
		else if(infolist.entities.length==0){
			$("#tbody").html(str);
			//document.getElementById('hiddenClass').style.display = "block";
			//document.getElementById('pageClass').style.display = "none";
		}
		//没有加载框，不需要隐藏
		//hideLoading();
	});
}



//点击修改按钮对弹框内的值做初始化  修改、回显sign
function update(enterpirseId){
	var id = enterpirseId;
	$("#title_id").text("修改信息");
	basePath+"/enterprise/selectByPrimaryKey.do"
	$("#updateApi").modal();
	//弹框同时加载数据
	$.get(basePath+"/enterprise/aEnterprise/detail.do?id="+id,
		function(data){
			var infolist = jQuery.parseJSON(data);
			$("#updateId").val(infolist.entity.id);
			$("#name").val(infolist.entity.name);
			$("#tradeId").val(infolist.entity.tradeId);
			$("#industryId").val(infolist.entity.industryId);
			$("#categoryId").val(infolist.entity.categoryId);
			$("#registerAddress").val(infolist.entity.registerAddress);
			$("#registerMajorBusiness").val(infolist.entity.registerMajorBusiness);
			$("#registerCapital").val(infolist.entity.registerCapital);
			$("#employeeCount").val(infolist.entity.employeeCount);
			$("#operateLocation").val(infolist.entity.operateLocation);
			$("#registerLocation").val(infolist.entity.registerLocation);
			$("#enterpriseType").val(infolist.entity.enterpriseType);
			$("#revenue").val(infolist.entity.revenue);
			$("#tax").val(infolist.entity.tax);
//				$("#registerTime").val(new Date(infolist.entity.registerTime));
			$("#registerTime").val(moment(infolist.entity.registerTime).format('YYYY-MM-DD'));
//				$("#createTime").val(new Date(infolist.entity.createTime));
			$("#linkman").val(infolist.entity.linkman);
			$("#phoneNumber").val(infolist.entity.registerLocation);
			$("#introduce").val(infolist.entity.introduce);
			$("#openFlag").val(infolist.entity.openFlag);
			$("#sourceLogId").val(infolist.entity.sourceLogId);
			//$("#streetId").val(infolist.entity.streetId);
			$("#streetName").val(infolist.entity.streetName);
			//$("#communityId").val(infolist.entity.communityId);
			$("#communityName").val(infolist.entity.communityName);

		});
}

/*改变title*/
function changeHead(that){
	var navi = $(".st-navi").find('small');
	$(navi[0]).find('span').html("<span onclick='window.location.reload()' style='cursor: pointer'>企业管理</span>&nbsp;&nbsp;&nbsp; <i class='icon-double-angle-right'></i>数据更新");
}

/*编辑企业*/
function editEnterprise(enterpriseId){
	var id  = enterpriseId;
	update(id);i
}

/*企业管理 点击编辑切换按钮*/
function enterpriseEditClick(){
    $(".colseBtn").css('display','none');
    $(".editBtn").css('display','none');
    $(".restBtn").css('display','block');
    $(".addBtn").css('display','block');
    $("#addEnIndustry").attr("disabled",false);
    $("#addEnIndustry").css("background-color","white");
    $("#addTrade").attr("disabled",false);
	$("#addTrade").css("background-color","white");
    $("#addCategory").attr("disabled",false);
    $("#addCategory").css("background-color","white");
    $("#employeeCount").attr("disabled",false);
    $("#phoneNumber").attr("disabled",false);
    $("#registerTime").attr("disabled",false);
    $("#registerCapital").attr("disabled",false);
    $("#openFlag").attr("readonly",false);
    $("#address").attr("disabled",false);
    $("#addEnStreet").attr("disabled",false);
	$("#addEnStreet").css("background-color","white");
    $("#addEnCommunity").attr("disabled",false);
    $("#addEnCommunity").css("background-color","white");
    $("#majorBusiness").attr("disabled",false);

	$("#openFlag").removeAttr("disabled");
	$("#openFlag").css("background-color","white");
}
/*企业管理 取消按钮*/
function cancelEnterpriseBtn(){
	var objId = $("#enterpriseId").val();
	var url = basePath + "/enterprise/selectByPrimaryKey.do";
	window.location.href = url + "?id=" + objId;
}

/*下拉框街道鼠标选中时改变对应的社区*/
function myEnStreet(){
	var streetId = $("#addEnStreet").val();
	$.ajax({
		type: "post",
		url: basePath+"/building/queryByStreetId.do",
		data: {"id":streetId},
		dataType: "json",
		success: function (data) {
			$("#addEnCommunity").find("option").remove();
			var str="";
			for(var i = 0;i<data.length;i++){
				var o=data[i];
				str += "<option value="+ o.communityId+">"+ o.communityName+"</option>"
				$("#addEnCommunity").html(str);
			}
		},
		error: function () {
		}
	});
}
/*根据产业ID找到下面对应的行业*/
function myIndustry(){
	var id = $("#addEnIndustry").val();
	 $.ajax({
	             type: "get",
	             url: basePath+"/enterprise/selectCategoryById.do",
	             data: {"id":id},
	             dataType: "json",
	             success: function (data) {
					 /*获取选择产业后得到的行业集合中第一个行业的信息*/
					 var firstTradeDataId = data[0].id;
					 var str="";
					 for(var i = 0;i<data.length;i++){
						 var o=data[i];

						 str += "<option value="+ o.id+">"+ o.name+"</option>"
						 $("#addTrade").html(str);
					 }
					 myTrade(firstTradeDataId);
	             },
	             error: function () {
	             }
	         });

}
/*根据行业ID找到下面对应的门类*/
function myTrade(){
	var id = $("#addTrade").val();
	$.ajax({
		type: "get",
		url: basePath+"/enterprise/selectCategoryById.do",
		data: {"id":id},
		dataType: "json",
		success: function (data) {
			var str="";
			for(var i = 0;i<data.length;i++){
				var o=data[i];
				str += "<option value="+ o.id+">"+ o.name+"</option>"
				$("#addCategory").html(str);
			}
		},
		error: function () {
		}
	});

}


/*删除企业*/
function delEnterprise(enterpriseId){
	var id = enterpriseId;
	$.ajax({
		type: "get",
		url: basePath+"/enterprise/deleteByPrimaryKey.do",
		data: {"id":id},
		success: function (result) {
			var gnl=confirm("你真的确定要删除吗?");
			if (gnl==true){
				window.location.reload();
				return true;
			}
			else{
				return false;
			}
		},
		error: function () {
		}
	});
}

/*关闭按钮*/
function closeEnterpriseBtn(){
	window.location.href=basePath+"/enterprise/list.do"
}