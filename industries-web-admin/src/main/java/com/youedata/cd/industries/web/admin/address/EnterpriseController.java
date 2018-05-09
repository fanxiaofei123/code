package com.youedata.cd.industries.web.admin.address;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseController;
import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.dao.category.CategoryDao;
import com.youedata.cd.industries.dao.enterprise.EnterpriseBaseMapper;
import com.youedata.cd.industries.dao.enterprise.EnterpriseLogMapper;
import com.youedata.cd.industries.pojo.category.Category;
import com.youedata.cd.industries.pojo.community.Community;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.pojo.street.Street;
import com.youedata.cd.industries.service.building.BuildingService;
import com.youedata.cd.industries.service.category.CategoryService;
import com.youedata.cd.industries.service.community.CommunityService;
import com.youedata.cd.industries.service.enterprise.EnterpriseBaseService;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import com.youedata.cd.industries.service.street.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


/**
 * Created by honshe on 2016/7/11.
 */
@Controller
@RequestMapping("enterprise")
public class EnterpriseController  extends BaseController<EnterpriseBase>{

	@Autowired
    private EnterpriseService enterpriseService;

	@Autowired
	private EnterpriseBaseService enterpriseBaseService;

	@Autowired
	private EnterpriseLogMapper enterpriseLogMapper;

	@Autowired
	private EnterpriseBaseMapper enterpriseBaseMapper;
	@Autowired
	private StreetService StreetService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private CategoryService  categoryService;
	@Autowired
	private BuildingService buildingService;



	@ResponseBody
	@RequestMapping(value = "deleteByPrimaryKey",method = RequestMethod.GET)
	public ResponseResult deleteByPrimaryKey(Long id){
		enterpriseBaseMapper.deleteByPrimaryKey(id);
		return new ResponseResult();
	}

	/*按照企业ID查找相关信息*/
/*按照企业ID查找相关信息*/
	@RequestMapping(value = "selectByPrimaryKey",method = RequestMethod.GET)
	public String selectByPrimaryKey(Model model, Long id,Long flag){
		EnterpriseBase enterpriseBase = enterpriseBaseMapper.selectByPrimaryKey(id);
		String registerTime = enterpriseBase.getRegisterTime();
		if(!"".equals(registerTime) && registerTime!=null){
			enterpriseBase.setRegisterTime(registerTime.substring(0,10));
		}
		model.addAttribute("enterpriseBase",enterpriseBase);
		/*查询所有街道*/
		List<Street> streets = StreetService.selectAll();
		/*查询所有社区*/
		List<Community>communitys = communityService.selectAll();
		Category category = new Category();
		Category category1 = new Category();
		Category category2 = new Category();
		List<Category>categoryIndustry = categoryService.queryByGrade(1);
		List<Category>categoryTrade = categoryService.queryByGrade(2);
		List<Category>categoryDoor = categoryService.queryByGrade(3);
		/*根据产业ID查找信息*/
		category = categoryDao.selectByPrimaryKey(Integer.parseInt(String.valueOf(enterpriseBase.getIndustryId())));
		List tradeIds = categoryService.selectCategoryById(Integer.parseInt(String.valueOf(enterpriseBase.getIndustryId())));
		/*根据行业ID查找信息*/
		category1 = categoryDao.selectByPrimaryKey(Integer.parseInt(String.valueOf(enterpriseBase.getTradeId())));
		List categoryIds = categoryService.selectCategoryById(Integer.parseInt(String.valueOf(enterpriseBase.getTradeId())));
		/*根据门ID查找信息*/
		category2 = categoryDao.selectByPrimaryKey(Integer.parseInt(String.valueOf(enterpriseBase.getCategoryId())));
		model.addAttribute("categoryIndustry",categoryIndustry);
		model.addAttribute("categoryTrade",categoryTrade);
		model.addAttribute("categoryDoor",categoryDoor);
		model.addAttribute("industry",category);
		model.addAttribute("trade",category1);
		model.addAttribute("category",category2);
		model.addAttribute("list",streets);
		model.addAttribute("cList",communitys);
		model.addAttribute("flagUpdate", flag);
		if (enterpriseBase.getStreetId()!=null){
			List communityIds = buildingService.queryByStreetId(Long.valueOf(enterpriseBase.getStreetId()));
			model.addAttribute("communitys",communityIds);

		}
		else{
			model.addAttribute("communitys","");
		}
		model.addAttribute("tradeIds",tradeIds);
		model.addAttribute("categoryIds",categoryIds);
		return "enterprise/from";
	}

	/*按照id查找信息*/
	@ResponseBody
	@RequestMapping(value = "selectCategoryById",method = RequestMethod.GET)
	public List selectCategoryById(Long id){
		List<Category> ids = new ArrayList<Category>();
		ids = categoryService.selectCategoryById(Integer.parseInt(String.valueOf(id)));
		return ids;
	}

	/**
	 e* @param enterpriseId
	 * @param enterpriseId
	 * @param tradeId
	 * @param industryId
	 * @param categoryId
	 * @param employeeCount
	 * @param phoneNumber
	 * @param registerTime
	 * @param registerCapital
	 * @param openFlag
	 * @param majorBusiness
	 * @param address
	 * @param streetId
	 * @param communityId
	 * @return
	 */
	/*更新企业表单*/
	@ResponseBody
	@RequestMapping(value = "updateByPrimaryKeySelective",method = RequestMethod.POST)
	public ResponseResult  updateByPrimaryKeySelective(Long enterpriseId,Long tradeId,Long industryId,Long categoryId,String employeeCount,
											String phoneNumber,String registerTime,String registerCapital,
											String openFlag,String majorBusiness,String address,Long streetId,Long communityId,String sourceLogId) {
		EnterpriseBase enterpriseBase = new EnterpriseBase();
		EnterpriseLog enterpriseLog = new EnterpriseLog();
		enterpriseBase.setId(enterpriseId);
		enterpriseBase.setTradeId(tradeId);
		enterpriseBase.setIndustryId(industryId);
		enterpriseBase.setCategoryId(categoryId);
		enterpriseBase.setEmployeeCount(employeeCount);
		enterpriseBase.setPhoneNumber(phoneNumber);
		enterpriseBase.setRegisterTime(registerTime);
		if (registerCapital=="" || "".equals(registerCapital)){
			enterpriseBase.setRegisterCapital("0");
		}
		else {
			enterpriseBase.setRegisterCapital(registerCapital.toString());
		}
		enterpriseBase.setOpenFlag(openFlag);
		enterpriseLog.setAddress(address);
		enterpriseLog.setId(sourceLogId);
		enterpriseLog.setEnterpriseId(enterpriseId);
		enterpriseLog.setMajorBusiness(majorBusiness);
		enterpriseLog.setStreetId(streetId);
		enterpriseLog.setCommunityId(communityId);
		enterpriseBaseService.updateByPrimaryKeySelective(enterpriseBase,enterpriseLog);
		ResponseResult responseResult = new ResponseResult();
		responseResult.setCode(200);
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("enterpriseId", enterpriseId);
		responseResult.setData(map);
		return responseResult;
		//return "enterprise/list";
	}





//	@Override
//	public IBaseService<EnterpriseBase> getBaseService() {
//		return enterpriseService;
//	}
//
//	@Override
//	public String getModelPre() {
//		return "/enterprise/";
//	}

	@Override
	public IBaseService<EnterpriseBase> get_base_service() {
		return enterpriseService;
	}

	@Override
	public String get_model_pre() {
		return "/enterprise/";
	}
}
