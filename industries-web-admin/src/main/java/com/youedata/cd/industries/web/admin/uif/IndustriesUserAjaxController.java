/*
 * FileName: GovaffRoleAjaxController.java
 * Copyright (C) 2016 youedata Tech. Co. Ltd. All Rights Reserved <piaomiaoxingz@126.com>
 * 
 * Licensed under the youedata License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : piaomiao <piaomiaoxingz@126.com>
 * date     : 2016年4月6日 下午4:24:21
 * last modify author :
 * version : 1.0
 */
/**
 * <p>Title: GovaffRoleAjaxController.java
 * <p>Description: 
 * <p>Copyright: (C) 2016 youedata Tech. Co. Ltd. All Rights Reserved <piaomiaoxingz@126.com>
 * <p>Company: www.youedata.com
 * @author piaomiao piaomiaoxingz@126.com
 * @date 2016年4月6日
 * @version 1.0
 */
package com.youedata.cd.industries.web.admin.uif;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.youedata.cd.common.constant.Constant;
import com.youedata.cd.common.util.MD5;
import com.youedata.cd.industries.pojo.user.UserInfo;
import com.youedata.cd.industries.service.user.UserInfoService;
import com.youedata.cd.industries.web.admin.base.AjaxJsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseAjaxController;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/uif/aUser")
public class IndustriesUserAjaxController extends
		BaseAjaxController<UserInfo> {
	@Autowired
	private UserInfoService userInfoService;

	/*
	 * (non-Javadoc) <p>Title: getBaseService <p>Description: 用户管理页面
	 * 
	 * @return
	 * 
	 * @see
	 * com.youedata.cd.base.web.controller.BaseAjaxController#getBaseService()
	 */
	@Override
	public IBaseService<UserInfo> getBaseService() {
		return userInfoService;
	}

	/**
	 * 密码修改
	 * @param t
	 * @param model
	 * @param request
	 * @param response
     * @param session
     */
	@RequestMapping("modifyUserInfo")
	@ResponseBody()
	public void modifyUserInfo(UserInfo t, Map model, HttpServletRequest request,
							   HttpServletResponse response, HttpSession session){
		if (t != null && t.getUserPassword() != null) {
			//获取当前登录用户
			UserInfo loginUser =(UserInfo)session.getAttribute(Constant.USER_KEY);
			List<UserInfo> Logins = userInfoService.selectPage(loginUser,0,1);
			if(Logins.size() == 0){
				AjaxJsonTools.sendFailJson(("数据异常"), response);
				return;
			}
			if(!loginUser.getUserPassword().equals(MD5.encode(t.getUserPassword()))){
				AjaxJsonTools.sendFailJson(("你输入的原密码不正确"), response);
				return;
			}else {
				UserInfo modifyUser = Logins.get(0);
				modifyUser.setUserPassword(MD5.encode(t.getUserPasswordNew()));
				userInfoService.update(modifyUser);
				session.removeAttribute(Constant.USER_KEY);
				AjaxJsonTools.sendAjaxOKJson(loginUser,"密码修改成功",response);
				return;
			}
		}

	}



	@RequestMapping("passwordVerify")
	@ResponseBody()
	public Boolean passwordVerify(String password, Map model, HttpServletRequest request,
							   HttpServletResponse response, HttpSession session){
		if (password != null) {
			//获取当前登录用户
			UserInfo loginUser =(UserInfo)session.getAttribute(Constant.USER_KEY);
			if(!loginUser.getUserPassword().equals(MD5.encode(password))){
				return false;
			}
		}
		return true;
	}




}
