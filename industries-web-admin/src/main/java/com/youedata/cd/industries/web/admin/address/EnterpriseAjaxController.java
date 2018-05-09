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
package com.youedata.cd.industries.web.admin.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseAjaxController;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.service.enterprise.EnterpriseBaseService;

@Controller
@RequestMapping("/enterprise/aEnterprise")
public class EnterpriseAjaxController extends BaseAjaxController<EnterpriseBase> {
	@Autowired
	private EnterpriseBaseService middleIndexDataService;
	
	@Override
	public IBaseService<EnterpriseBase> getBaseService() {
		return middleIndexDataService;
	}
	
	
}
