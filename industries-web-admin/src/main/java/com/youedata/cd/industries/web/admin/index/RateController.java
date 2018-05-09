package com.youedata.cd.industries.web.admin.index;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseController;
import com.youedata.cd.index.pojo.IndexRate;
import com.youedata.cd.industries.service.index.IndexRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/indexRate")
public class RateController extends BaseController<IndexRate> {
    
 	@Autowired
	private IndexRateService indexRateService;
	
	@Override
	public IBaseService<IndexRate> get_base_service() {
		return indexRateService;
	}
	
	@Override
	public String get_model_pre() {
		return "indexDefinition/";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(IndexRate indexRate, RedirectAttributes redirectAttributes) {
		indexRateService.save(indexRate);
		redirectAttributes.addFlashAttribute("msg", "保存成功");
/*		redirectAttributes.addFlashAttribute("industryId", indexRate.getIndustryId());*/
		return "redirect:/indexDefinition/getIndexDefinitionByIndustry.do?industryId="+ indexRate.getIndustryId();
	}

}
