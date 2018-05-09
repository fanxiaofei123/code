/*
 * FileName: GovaffRoleController.java
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
 * date     : 2016年4月5日 上午11:05:16
 * last modify author :
 * version : 1.0
 */
/**
 * <p>Title: GovaffRoleController.java
 * <p>Description: 
 * <p>Copyright: (C) 2016 youedata Tech. Co. Ltd. All Rights Reserved <piaomiaoxingz@126.com>
 * <p>Company: www.youedata.com
 * @author piaomiao piaomiaoxingz@126.com
 * @date 2016年4月5日
 * @version 1.0
 */
package com.youedata.cd.industries.web.admin.uif;

import com.youedata.cd.industries.pojo.category.Category;
import com.youedata.cd.industries.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseController;

@Controller
@RequestMapping("/uif/user")
public class IndustriesUserController extends BaseController<Category> {

	@Autowired
	private CategoryService categoryService;
	/* (non-Javadoc) 
	 * <p>Title: get_base_service
	 * <p>Description: 
	 * @return
	 * @see com.youedata.cd.web.controller.BaseController#get_base_service()
	 */


	/* (non-Javadoc)
	 * <p>Title: get_model_pre
	 * <p>Description: 
	 * @return
	 * @see com.youedata.cd.web.controller.BaseController#get_model_pre()
	 */
	@RequestMapping("modify")
	public String showModefiyPage() {
		return "uif/user/userinfoModify";
	}




//
//	@Override
//	public IBaseService<Category> getBaseService() {
//		return categoryService;
//	}
//
//	@Override
//	public String getModelPre() {
//		return "uif/user/";
//	}

	@Override
	public IBaseService<Category> get_base_service() {
		return categoryService;
	}

	@Override
	public String get_model_pre() {
		return "uif/user/";
	}
}
