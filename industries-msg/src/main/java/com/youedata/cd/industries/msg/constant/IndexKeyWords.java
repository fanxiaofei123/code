/*
 * FileName: KeyWords.java
 * Copyright (C) 2016 youedata Tech. Co. Ltd. All Rights Reserved <admin@youedata.com>
 * 
 * Licensed under the youedata License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : yy <admin@youedata.com>
 * date     : 2016年3月15日 上午10:20:15
 * last modify author :
 * version : 1.1
 */
package com.youedata.cd.industries.msg.constant;
/**
 * <p>Title: KeyWords.java
 * <p>Description: 
 * <p>Copyright: (C) 2016 youedata Tech. Co. Ltd. All Rights Reserved <admin@youedata.com>
 * <p>Company: www.youedata.com
 * @author yy admin@youedata.com
 * @date 2016年3月15日
 * @version 1.1
 */


public interface IndexKeyWords {
	String PARAM_ERROR = "参数有误";
	String PARAM_EMPTY = "参数为空";
	String INDEX_NON_EXISTENT = "您配置的指标在当前指标体系中不存在";
	/**
	 * 柱状图
	 */
	String BAR = "bar";
	/**
	 * 饼状图
	 */
	String PIE = "pie";
	
	/**
	 * 雷达图
	 */
	String RADAR = "radar";
	
	/**
	 * 界面传的待获取数据是否规则：0 规则 1不规则
	 */
   	String RULE_TYPE_ZERO = "0";
   	
	/**
	 * 界面传的待获取数据是否规则：0 规则 1不规则
	 */
   	String RULE_TYPE_ONE = "1";
   	
   	String SYS_EXCEPTION = "系统异常";
   	
   	String TIME_FORMAT_FAIL = "您传的时间格式有误";
   	
   	String INDEX_NO_EXIST = "您传的指标不存在或不在当前指标体系中";
   	
   	/**
   	 * 查询成功
   	 */
   	String MSG_QUERY_SUCCESS = "查询成功";
   	
   	String KEY_DATETIME = "dateTimes";
   	String KEY_DATAS = "datas";
   	String KEY_TIMES = "times";
   	String KEY_TIMES_DATAS = "timesData";
   	String KEY_LOCATIONS = "locations";
   	String KEY_CHART_TITLE = "chartTitle";
   	String KEY_CHART_DESC = "chartDesc";
   	String KEY_EVENTS = "events";
   	String KEY_INDEX_DESC = "indexDesc";
   	String KEY_UNITS = "units";
   	String KEY_INDEX_NAME = "indexNames";
   	String KEY_INDEX_AREAS = "indexAreas";
	String KEY_OFFSET = "offset";
	
	/**
	 * 全国双创指数，和数据库对应
	 */
	String COUNTRY_INNOVATION = "20160913173044";
}
