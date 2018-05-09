<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 修改弹窗 及保存弹窗  -->
<div class="modal fade" id="updateApi" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
<!-- 原始大小弹出框 -->
     <div class="modal-dialog" role="document">
<!--     增大弹出框 -->
<!--     <div class="modal-dialog modal-lg" role="document"> -->
<!--     全屏弹出框 -->
<!-- 	<div class="modal-dialog modal-full" role="document"> -->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close" name="close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="title_id">新建信息</h4>
        </div>
        <form  id="updatedata"  name="subForm" method="post" enctype="multipart/form-data" >
        <div class="modal-body" id="newsDiv">
          <div class="form-group form-inline">
          	<input name="id" id="updateId" type="hidden">
            <label for="name" ><span style = "color:red">*</span>用户名称：</label>
            <input id="name" type="text" name="name" class="form-control input-insert-modal"  autocomplete = 'off'>
          </div>
          
         <div class="form-group form-inline">
         			<label for="userPassword" ><span style = "color:red">*</span>密&emsp;&emsp;码：</label>
            		<input id="userPassword"    type="password" name="userPassword" class="form-control input-insert-modal" >
		 </div>
         <div class="form-group form-inline">
         			<label for="userPassword" ><span style = "color:red">*</span>确认密码：</label>
            		<input id="userPasswordNew" type="password" name="userPasswordNew"  class="form-control input-insert-modal" >
		 </div>
         <div class="form-group form-inline" >
         			<label for="userNickname" ><span style = "color:red">*</span>昵&emsp;&emsp;称：</label>
            		<input id="userNickname"  type="text" name="userNickname" class="form-control input-insert-modal" >
		 </div>
		 <div class="form-group form-inline">
					 <label for="logo">头像缩略图：</label>
           <input id="addImgId" name="file" type="file" accept="image/gif,image/jpeg,image/png" onchange="javascript:setImagePreview('addImgId','addimgurlId');" />
				<span style="color:#DE9D1A">请上传 *.jpg,*.png,*.gif格式图片</span>
		        <br/>
				<img id="addimgurlId" alt="请上传图片" style="diplay:none;"/>
		 </div>
		 
         <div class="form-group form-inline" >
         			<label for="userPhone" ><span style = "color:red">*</span>电话号码：</label>
            		<input id="userPhone"  type="text" name="userPhone" class="form-control input-insert-modal" >
		 </div>
          
        </div>
        <div class="modal-footer">
        	<button type="submit"  class="btn btn-warning"  >保存</button>
          <button type="button" class="btn btn-info" data-dismiss="modal" name="close">关闭</button>
        </div>
        </form>
      </div>
    </div>
 </div>