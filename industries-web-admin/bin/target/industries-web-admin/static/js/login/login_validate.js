//自定义验证
$().ready(function() {
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
});

//验证规则
function getRules(){
	var rules = {	
	    name:{
    		 required: true,
    		 rangelength:[2,16],
    		 isName:true
    	},
    	userPassword: {
    		 required: true,
    		// rangelength:[6,15],
    		 //isPassword:true
	     },
	     verifyCode: {
	    	 required: true
	     }
	};
	return rules;
}
//验证消息
function validateMessages(){
	  var messages = {
			        name: {
			    		required: "用户名必填",
			    		rangelength:"用户名要在3至16个字符之间"
			      	},
			      	userPassword: {
			      		required:"密码必填",
			    		rangelength:"密码必须在6-15位之间"
				    },
				    verifyCode:{
			      		required: "验证码必填"
			      	}
			    };
	  return messages;
}