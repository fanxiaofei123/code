$(function () {
	getVerifyCode()
	afreashVerifyCode();
	//监听docuemnt的onkeydown事件看是不是按了回车键
	$(document).keydown(function(event){
		event = event ? event : window.event;
		if (event.keyCode === 13){
			$("#loginBtn").trigger("click");
		}
	});

	//ajax提交表单
	function updateData(form){
		showLoading("正在登陆，请稍后...");
		$('#updateData').ajaxSubmit({
		     type:"post",
		     dataType:"json",
		     url:basePath+"/login.do",
		     success: function(data){
		    	 	hideLoading();	            
	            	if (data != null && data.status == "200") {  //如果登录不成功，则再次刷新验证码
	            		// window.location.href = basePath + "/data/enterpriseDataUpdates.do";
	            		window.location.href = basePath + "/enterprise/list.do";
					}else{
						var txt = data.msg;
						//window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
	            		//clearLoginForm();//清除信息
						top.layer.alert(txt);
					}
	            	
		     }
	});
	}	
	
	//验证提交表单
	 $("#updateData").validate({
	        submitHandler:updateData,
		    rules: getRules(),
	        messages:validateMessages()
	  });
});

function  afreashVerifyCode(){
	$("#vimg").on('click', function () {
			getVerifyCode();
		}
	);
}


//刷新验证码
function getVerifyCode() {
	$("#vimg").attr("src", basePath + "/validateCode/randCode.do?random=" + Math.random());
}

function clearLoginForm(){	
	$("#verifyCode").val("");
	$("#userPassword").val("");
	getVerifyCode();
}