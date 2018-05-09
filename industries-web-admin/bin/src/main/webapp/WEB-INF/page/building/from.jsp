<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row"  id="update" style="display: none">
    <%--数据更新左边--%>
    <div class="col-xs-4" style="float: left">
        <h4>楼宇名称:&nbsp;&nbsp;&nbsp;<span id="bName">XXX大厦</span></h4>

        <br>
        <h4>楼宇地址:&nbsp;&nbsp;&nbsp;<span id="bAddress">XXX地址</span></h4>
        <br/>
        <span class="addNewEnterprise" style="display: none"><button type="button" class="btn btn-primary btn-xs" onclick="addNewEnterprise()">新增企业</button></span>
    </div>
    <%--数据更新右边--%>
    <form  method="post" id="updateForm">
    	<input type="text" id="building_id" name="id" style="display: none;">
    <div class="col-xs-8"  style="float: left">
       <table id="mytable" class="build-table">
           <tr style="display: block;margin: 8px 0">
               <td><span>所在街道</span></td>
               <td style="padding-left: 13px">
                   <select id="addStreet" name="streetId" style="width: 163px;height: 28px" onclick="myStreet()">
                       <option value="街道1">暂无数据</option>
                   </select>

               </td>
           </tr>
            <tr style="display: block;margin: 8px 0">
                <td>
                    <span>所在社区</span>
                </td>
                <td style="padding-left: 13px">
                    <select id="addCommunity" name="communityId"  style="width: 163px;height: 28px">
                        <option value="社区1"></option>
                    </select>
                </td>
            </tr>
       </table>
        <span class="restBtn" style="display: none;float: left;margin-left: 145px"><button type="button"  class="btn btn-primary btn-xs" value="取消" onclick="cancelBtn()">取消</button></span>
        <span class="addBtn" style="display: none;float: left;margin-left: 2px"><button type="button" class="btn btn-primary btn-xs" onclick="updateInfo()">更新</button></span>
        <span class="colseBtn" style="float: left;margin-left: 145px"><button type="button" class="btn btn-primary btn-xs" value="关闭" onclick="closeBtn()">关闭</button></span>
        <span class="editBtn" style="float: left;margin-left: 2px"><button type="button" id="btnOK" class="btn btn-primary btn-xs" onclick="editClick()">编辑</button></span>

    </div>
    </form>
</div>