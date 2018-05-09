/*
 * FileName: PcLoginController.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : xbearin@gmail.com
 * date     : 2014年9月14日 下午8:48:47
 * last modify author :
 * version : 1.0
 */
/**
 * <p>Title: PcLoginController.java
 * <p>Description: 
 * <p>Copyright: (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * <p>Company: www.plusub.com
 * @author xbearin@gmail.com
 * @date 2014年9月14日
 * @version 1.0
 */
package com.youedata.cd.industries.web.admin.uif;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.alibaba.fastjson.JSONObject;
import com.youedata.cd.base.common.constant.KeyWords;
import com.youedata.cd.common.constant.Constant;
import com.youedata.cd.common.util.MD5;
import com.youedata.cd.industries.pojo.user.UserInfo;
import com.youedata.cd.industries.service.user.UserInfoService;
import com.youedata.cd.industries.web.admin.base.AjaxJsonTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 
 * <p>Title: LoginController.java
 * <p>Description: 登录的控制器
 * <p>Copyright: (C) 2016 youedata Tech. Co. Ltd. All Rights Reserved <admin@youedata.com>
 * <p>Company: www.youedata.com
 * @author yy admin@youedata.com
 * @date 2016年3月15日
 * @version 1.1
 */
@Controller
public class LoginController{

    /* @Autowired
     private InnovationUifUserService innovationUifUserService;*/
    @Autowired
    private UserInfoService userInfoService;
    
    @RequestMapping(value = "loginPage", method = RequestMethod.GET)
    public String to_login(HttpServletRequest request, HttpServletResponse response){
        return "login";
    }
    @RequestMapping(value = "updataPass", method = RequestMethod.GET)
    public String updataPass(HttpServletRequest request, HttpServletResponse response){
        return "updataPass";
    }
    
    /**
     * 
     * <p>Title: login
     * <p>Description: 用户登录
     * @author yy admin@youedata.com
     * @date 2016年3月15日
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(UserInfo user, HttpServletRequest request, HttpServletResponse response,
                      HttpSession session) throws IOException {
    	
    	if (StringUtils.isEmpty(user.getName())) {
    		AjaxJsonTools.sendFailJson("用户名为空", response);
    		return;
		}

    	Object verifyCode = request.getSession().getAttribute(Constant.RANDOM_CODE_KEY);
        if (verifyCode != null && StringUtils.isNotEmpty(user.getVerifyCode())) {
            if (!user.getVerifyCode().toLowerCase().equals(verifyCode.toString().toLowerCase())) {
            	AjaxJsonTools.sendFailJson("验证码有误，请重新输入", response);
        		return;
        	}
        }
 

        UserInfo  uifUser =   userInfoService.selectByName(user.getName());

        if(uifUser ==null){
            AjaxJsonTools.sendFailJson("对不起账户不存在", response);
            return;
        }
       /**
        InnovationUifUser uifUser = userList.get(0);
        if (StringUtils.isNotEmpty(uifUser.getHeadPic())) {
        	uifUser.setHeadPic(UploadPathConfig.IMAGES_SERVER_URL + uifUser.getHeadPic());
        }*/

        //UserInfo uifUser = userList.get(0);
        //密码匹配
        if (!uifUser.getUserPassword().equals(MD5.encode(user.getUserPassword().trim()))) {
            AjaxJsonTools.sendFailJson("对不起密码错误", response);
    		return;
        }
        System.err.println("uifUser"+uifUser);
        JSONObject json = new JSONObject();
    	json.put(KeyWords.STATUS, KeyWords.STATUS_OK);
    	json.put(KeyWords.ENTITY, user);
        session.setAttribute(Constant.USER_KEY, uifUser);
    	AjaxJsonTools.sendJson(json, response);
    }

    /**
     * 注销
     * 
     * @param request
     * @param response
     * @param session
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        session.removeAttribute(Constant.USER_KEY);
        return "login";
    }
}
