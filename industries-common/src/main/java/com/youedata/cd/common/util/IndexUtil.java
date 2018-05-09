package com.youedata.cd.common.util;

public class IndexUtil {
	public static String assembleTimeCondition(String validTimeStr) {
		String vTimeCondition = "";
		if(YoueStringUtils.isEmpty(validTimeStr)) {
			return vTimeCondition;
		}
		if (DateUtil.isYear(validTimeStr)) {
			vTimeCondition = "%Y";
		} else if (DateUtil.isMonth(validTimeStr)){
			vTimeCondition = "%m";
		} else if (DateUtil.isYearAndMonth(validTimeStr)) {
			vTimeCondition = "%Y-%m";
		} else if (DateUtil.isDate(validTimeStr)) {
			vTimeCondition = "%Y-%m-%d";
		}
		return vTimeCondition;
	}
}
