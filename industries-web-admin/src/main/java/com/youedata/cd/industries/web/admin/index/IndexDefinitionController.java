package com.youedata.cd.industries.web.admin.index;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.youedata.cd.base.common.constant.ReturnUrls;
import com.youedata.cd.base.web.controller.BaseController;
import com.youedata.cd.common.util.YoueStringUtils;
import com.youedata.cd.industries.service.index.IndexDefinitionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.index.pojo.IndexDefinition;

@Controller
@RequestMapping("/indexDefinition")
public class IndexDefinitionController extends BaseController<IndexDefinition> {
    
	@Autowired
	private IndexDefinitionService indexDefinitionService;
	
	@Override
	public IBaseService<IndexDefinition> get_base_service() {
		return indexDefinitionService;
	}
	
	@Override
	public String get_model_pre() {
		return "indexDefinition/";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String form(IndexDefinition indexDefinition, Model model, HttpServletRequest request) {
		if (null != indexDefinition.getId()) {
			indexDefinition = indexDefinitionService.get(indexDefinition.getId());
		}
		model.addAttribute("indexDefinition", indexDefinition);
		
		return "definition/form";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(IndexDefinition indexDefinition, Model model, RedirectAttributes redirectAttributes) {
		if (indexDefinition.getParent() != null
				&& indexDefinition.getParent().getId() == null) {
			indexDefinition.getParent().setId(0l);
		}
		indexDefinitionService.saveOrUpdate(indexDefinition);
		return "redirect:listAll.do";
	}

	@RequestMapping({"/getIndexDefinitionByIndustry"})
	public String getIndexDefinitionByIndustry(Map model, HttpServletRequest request, HttpServletResponse response) {
		String industryIdStr = request.getParameter("industryId");
		//默认为第一产业
		if (YoueStringUtils.isEmpty(industryIdStr)) {
			industryIdStr = "1";
		}
		Long industryId = Long.parseLong(industryIdStr);
		List<IndexDefinition> list = Lists.newArrayList();
		List<IndexDefinition>indexDefinitionList = indexDefinitionService.getIndexDefinitionByIndustry(industryId);
		IndexDefinition.sortList(list, indexDefinitionList, IndexDefinition.getRootId(), true);

		model.put("entities", list);
		model.put("industryId", industryId);
		return ReturnUrls.getList(this.get_model_pre());
	}

	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<IndexDefinition> list = indexDefinitionService.selectAll();
		for (int i=0; i<list.size(); i++){
			IndexDefinition index = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(index.getId()))){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", index.getId());
				map.put("pId", index.getParentId());
				map.put("name", index.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	 
}
