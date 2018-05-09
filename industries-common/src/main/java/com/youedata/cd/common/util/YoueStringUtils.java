package com.youedata.cd.common.util;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * 
 * @author yangpengcheng
 * 
 */
public class YoueStringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 将一个日期转换成String 方法名：getDateString<BR>
	 * 创建人：yangpengcheng <BR>
	 * 时间：2014年8月11日-下午9:59:14 <BR>
	 * 
	 * @param date
	 * @param pattern
	 * @return String<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static String formatDateToString(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 将日期字符串转换成Date 方法名：getDateString<BR>
	 * 创建人：yangpengcheng <BR>
	 * 时间：2014年8月11日-下午10:04:06 <BR>
	 * 
	 * @param dateString
	 * @param pattern
	 * @return
	 * @throws ParseException
	 *             Date<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static Date parseStringToDate(String dateString, String pattern)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(dateString);
	}

	/**
	 * 
	 * 将小数格式化成字符串，会进行四舍五入 如：3656.4554===结果:3656.46<BR>
	 * 方法名：formatDoubleToString<BR>
	 * 创建人：yangpengcheng <BR>
	 * 时间：2014年8月12日-下午9:12:01 <BR>
	 * 
	 * @param dou
	 * @return String<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static String formatDoubleToString(double dou, String format) {
		if (isEmpty(format))
			format = "#.##";
		DecimalFormat decimalFormat = new DecimalFormat(format);
		String string = decimalFormat.format(dou);// 四舍五入，逢五进一
		return string;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}

	/**
	 * 非空判断 (这里用一句话描述这个方法的作用)<BR>
	 * 方法名：isNotEmpty<BR>
	 * 创建人：yangpengcheng <BR>
	 * 时间：2014年8月12日-下午9:36:18 <BR>
	 * 
	 * @param str
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 百分比转换 方法名：getPercent<BR>
	 * 创建人：yangpengcheng <BR>
	 * 时间：2014年8月12日-下午9:50:46 <BR>
	 * 
	 * @param num
	 * @param totalCount
	 * @param objects
	 * @return String<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static String getPercent(int num, double totalCount,
			String... objects) {
		String format = "#.##";
		if (objects != null && objects.length > 0) {
			format = objects[0];
		}
		return YoueStringUtils.formatDoubleToString((num / totalCount) * 100,
				format) + "%";
	}

	/**
	 * 百分比转换 方法名：getPercent<BR>
	 * 创建人：yangpengcheng <BR>
	 * 时间：2014年8月12日-下午9:50:46 <BR>
	 * 
	 * @param num
	 *            当前的数字
	 * @param totalCount
	 *            总数
	 * @param objects
	 * @return String<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static String getPercent(int num, float totalCount,
			String... objects) {// 动态参数
		String format = "#.##";
		if (objects != null && objects.length > 0) {
			format = objects[0];
		}
		return YoueStringUtils.formatDoubleToString((num / totalCount) * 100,
				format) + "%";
	}

	/**
	 * 冒泡排序方法,如果为true那就是降序，false那么久是升序 方法名：sorts<BR>
	 * 创建人：yangpengcheng <BR>
	 * 时间：2014年8月12日-下午11:35:55 <BR>
	 * 
	 * @param datas
	 * @param flag
	 * @return int[]<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static int[] sorts(int[] datas, boolean flag) {
		for (int i = 0; i < datas.length; i++) {// 轮询次数
			for (int j = 0; j < datas.length - 1; j++) {// 交换次数
				if (flag) {
					if (datas[j] < datas[j + 1]) {
						int temp = datas[j];
						datas[j] = datas[j + 1];
						datas[j + 1] = temp;
					}
				} else {
					if (datas[j] < datas[j + 1]) {
						int temp = datas[j];
						datas[j] = datas[j + 1];
						datas[j + 1] = temp;
					}
				}
			}
		}
		return datas;
	}

	/**
	 * 凯德加密 方法名：encryption<BR>
	 * 创建人：yangpengcheng <BR>
	 * 时间：2014年10月25日-下午9:48:19 <BR>
	 * 
	 * @param str
	 * @param k
	 * @return String<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static String encryption(String str, int k) {
		String string = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 'a' && c <= 'z') {
				c += k % 26;
				if (c < 'a') {
					c += 26;
				}
				if (c > 'z') {
					c -= 26;
				}
			} else if (c >= 'A' && c <= 'Z') {
				c += k % 26;
				if (c < 'A') {
					c += 26;
				}
				if (c > 'Z') {
					c -= 26;
				}
			}
			string += c;
		}
		return string;
	}

	/**
	 * 凯德解密 方法名：dencryption<BR>
	 * 创建人：yangpengcheng <BR>
	 * 时间：2014年10月25日-下午9:48:35 <BR>
	 * 
	 * @param str
	 * @param n
	 * @return String<BR>
	 * @exception <BR>
	 * @since 1.0.0
	 */
	public static String dencryption(String str, int n) {
		String string = "";
		int k = Integer.parseInt("-" + n);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 'a' && c <= 'z') {
				c += k % 26;
				if (c < 'a') {
					c += 26;
				}
				if (c > 'z') {
					c -= 26;
				}
			} else if (c >= 'A' && c <= 'Z') {
				c += k % 26;
				if (c < 'A') {
					c += 26;
				}
				if (c > 'Z') {
					c -= 26;
				}
			}
			string += c;
		}
		return string;
	}

	/**
	 * 文件后缀处理
	 * 
	 * @param oldExt
	 * @return
	 */
	public static String ext(String oldExt) {
		String result = oldExt;
		if (!oldExt.equals("") && oldExt != null) {
			if (oldExt.toLowerCase().equals("xlsx")
					|| oldExt.toLowerCase().equals("xlsx"))
				result = "xls";
			if (oldExt.toLowerCase().equals("docx")
					|| oldExt.toLowerCase().equals("doc"))
				result = "word";
		}
		return result;
	}

	public static boolean isImage(String ext) {
		return ext.toLowerCase().matches("jpg|gif|bmp|png|jpeg");
	}

	public static boolean isDoc(String ext) {
		return ext
				.toLowerCase()
				.matches(
						"doc|docx|xls|xlsx|pdf|txt|ppt|pptx|rar|zip|html|jsp|sql|htm|shtml|xml");
	}

	public static boolean isVideo(String ext) {
		return ext.toLowerCase().matches("mp4|flv|mp3|rmbv|avi");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * 
	 * @param str
	 *            目标字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null || "".equals(str)) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str))
					.toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 0) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isEmpty(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}
    
	/**
	 * list转化成string以逗号隔开
	 * @param stringList
	 * @return
	 */
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	public static String nullObjToEmptyStr(Object obj) {
		return obj ==null ? "" : (String)obj;
	}

	public static Long nullObjToEmptyLong(Object obj) {
		return obj == null ? 0 : (Long)obj;
	}

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString){
    	StringBuilder result = new StringBuilder();
    	StringBuilder val = new StringBuilder();
    	String[] vals = split(objectString, ".");
    	for (int i=0; i<vals.length; i++){
    		val.append("." + vals[i]);
    		result.append("!"+(val.substring(1))+"?'':");
    	}
    	result.append(val.substring(1));
    	return result.toString();
    }
	
	public static String uuid() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String seconds = new SimpleDateFormat("HHmmss").format(new Date());
		return date+getTwo()+"00"+seconds+getTwo();
	}

	/**
	 * 产生随机的2位数
	 * @return
	 */
	public static String getTwo(){
		Random rad=new Random();
		String result  = rad.nextInt(100) +"";
		if(result.length()==1){
			result = "0" + result;
		}
		return result;
	}

   public static BigDecimal formatBigDecimal(BigDecimal data) {
	   NumberFormat nf = NumberFormat.getInstance();
	   return new BigDecimal(nf.format(data));
   }

   public static Long strToLong(String str) {
   	if (isEmpty(str)) {
   		return null;
	}
	return Long.parseLong(str);
   }

	public static void main(String[] args) {
		System.out.println(uuid());
	}

}
