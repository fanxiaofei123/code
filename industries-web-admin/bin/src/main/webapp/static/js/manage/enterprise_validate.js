//自定义验证
$().ready(function() {	 
	  /**校验总数/金额格式 支持 数字 */
	  jQuery.validator.addMethod("isNumber", function(value, element){
		  return /^([0-9]*|0)$/i.test(value) | /^[0-9]*\.[0-9]*$/i.test(value);
	  }, "请输入0或正数"); 
	  
	  /**校验年份格式 支持 数字 */
	  jQuery.validator.addMethod("isYears", function(value, element){
		  if(value<2100 && value> 1900) {
			  return /^[0-9]+$/i.test(value);
		  }
		  return false;
	  }, "请填写1900到2100之间的年份");
});

//验证规则
function getRules(){
	var rules = {
		  registerTime: {
			  required: true,
			  date:true
		  }
	};
	return rules;
}
//验证消息
function validateMessages(){
	  var messages = {
			  registerTime: {
				  required: "日期不能为空",
				  date:"格式如:1990-01-01"
			  }
	 };
	  return messages;
}