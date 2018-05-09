//自定义验证
$().ready(function() {
	 /**校验手机号码格式 支持 13 15(0-3,5-9) 170 177字段 */
	  jQuery.validator.addMethod("isMobile", function(value, element){
	    var length = value.length;
	    return this.optional(element) || length == 11 && /^((13[0-9])|(15[0-3，5-9])|(18[0-9])|(170)|(177))\d{8}$/.test(value);
	  }, "请填写正确的手机号码"); 
	  /**校验密码格式 支持 数字和字母(字符A-Z, a-z, 0-9 6-15位 */
	  jQuery.validator.addMethod("isPassword", function(value, element){
		  /*
		   * 1.首字母为字母
		   * 2.必须有字母数字，不能纯数字或字母
		   * 3.可以包含_,但必须包含数字
		   */
		  return /^[a-zA-Z]{1}(?![_a-zA-Z]+$)[\w]{5,14}$/.test(value);
	  }, "密码格式不正确"); 
	  /**校验用户名格式 支持 中文或英文 */
	  jQuery.validator.addMethod("isName", function(value, element){
		  return /^[\u0391-\uFFE5]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
	  }, "请填写正确的用户名");


	jQuery.validator.addMethod("notEqualTo", function (value, element, param) {
		return value != $(param).val();
	}, $.validator.format("旧密码和新密码不能相同!"));


	jQuery.validator.addMethod("isPwd", function(value, element) {
		return this.optional(element) || /^[a-zA-Z]\\w{6,12}$/.test(value);
	}, "以字母开头，长度在6-12之间，只能包含字符、数字和下划线。");


	/*jQuery.validator.addMethod("isPwdt", function(value, element) {
		return this.optional(element) || /^[a-zA-Z]\\w{6,12}$/.test(value);
	}, "以字母开头，长度在6-12之间，只能包含字符、数字和下划线。");*/
	$.validator.addMethod("strongPsw", function(value, element) {
		if(passwordLevel(value) == 1){return false;}
		return true
	}, "密码只能包括中文字、英文字母、数字和下划线、且至少为上面两种!");
});

//验证规则
function getRules(){
	var rules = {	
    	name:{
    		 required: true,
    		 rangelength:[1,10],
    	},
		userPassword:{
    		 required: true,
	      },
		longitude:{
			number:true
		},
		latitude:{
			number:true
		},
		userPasswordNew: {
	    	  required: true,
			  notEqualTo:"input[name=userPassword]",
			  minlength: 6,        //最小长度
			  maxlength: 32,        //最大长度
			  strongPsw: true    //密码强度

	      },
		userPasswordConfirm: {
	    	  required: true,
	    	  equalTo:"input[name=userPasswordNew]"
	      },
	      userNickname: {
	    	  required: true,
	    	  rangelength:[2,16]
	      },
	      userPhone: {
	    	  required: true,
			  digits:true,
			  isMobile:true
	      }
	};
	return rules;
}
//验证消息
function validateMessages(){
	  var messages = {
			    	name: {
			    		required: "名称必填",
			    		rangelength:"名称太长"
			      	},
		  			longitude:{
						number:"经度必须为数字"
					},
		 			 latitude:{
						number:"纬度必须为数字"
					},
		            userPassword: {
			      		required:"密码必填"
				      },
				      userPasswordNew: {
				    	  required: "确认密码必填",
						  rangelength:"密码要在3至16个字符之间"
			      	  },
		  			userPasswordConfirm: {
				    	  required: "确认密码必填",
				    	  equalTo:"两次密码输入不一致，请重新输入"
			      	  },
			      	userPhone:{
			      		  required: "手机号必填" ,
						  digits:"手机号必须是正整数"
			      	  }
			    };
	  return messages;
}


function passwordLevel(password) {
	var Modes = 0;
	for (i = 0; i < password.length; i++) {
		Modes |= CharMode(password.charCodeAt(i));
	}
	return bitTotal(Modes);

	//CharMode函数
	function CharMode(iN) {
		if (iN >= 48 && iN <= 57)//数字
			return 1;
		if (iN >= 65 && iN <= 90) //大写字母
			return 2;
		if ((iN >= 97 && iN <= 122) || (iN >= 65 && iN <= 90)) //大小写
			return 4;
		else
			return 8; //特殊字符
	}

	//bitTotal函数
	function bitTotal(num) {
		modes = 0;
		for (i = 0; i < 4; i++) {
			if (num & 1) modes++;
			num >>>= 1;
		}
		return modes;
	}
}

