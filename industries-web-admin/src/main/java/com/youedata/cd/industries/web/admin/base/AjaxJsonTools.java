/**
 * 
 */
package com.youedata.cd.industries.web.admin.base;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;

import com.youedata.cd.base.common.constant.KeyWords;

/**
 * @author feng
 */

@Controller
public class AjaxJsonTools {

	public static void sendJson(JSONObject json,HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().write(json.toJSONString());
		} catch (Exception e) {
			try {
				JSONObject object2 = new JSONObject();
				object2.put(KeyWords.STATUS, KeyWords.STATUS_FAIL);
				object2.put(KeyWords.MESSAGE, KeyWords.MESSAGE_DEFAULT_VALUE);
				response.getWriter().write(object2.toJSONString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void send(String str,HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().write(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendFailJson(String msg,HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		try {
			JSONObject object2 = new JSONObject();
			object2.put(KeyWords.STATUS, KeyWords.STATUS_FAIL);
			object2.put(KeyWords.MESSAGE, msg);
			response.getWriter().write(object2.toJSONString());
		} catch (Exception e) {
			try {
				JSONObject object2 = new JSONObject();
				object2.put(KeyWords.STATUS, KeyWords.STATUS_FAIL);
				object2.put(KeyWords.MESSAGE, KeyWords.MESSAGE_DEFAULT_VALUE);
				response.getWriter().write(object2.toJSONString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void sendOKJson(String msg,HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		try {
			JSONObject object2 = new JSONObject();
			object2.put(KeyWords.STATUS, KeyWords.STATUS_OK);
			object2.put(KeyWords.MESSAGE, msg);
			response.getWriter().write(object2.toJSONString());
		} catch (Exception e) {
			try {
				JSONObject object2 = new JSONObject();
				object2.put(KeyWords.STATUS, KeyWords.STATUS_FAIL);
				object2.put(KeyWords.MESSAGE, KeyWords.MESSAGE_DEFAULT_VALUE);
				response.getWriter().write(object2.toJSONString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}


	@SuppressWarnings("unchecked")
	public static void sendAjaxOKJson(Object object,String name,HttpServletResponse response) {
		try {
			JSONObject object2 = new JSONObject();
			object2.put(KeyWords.STATUS, KeyWords.STATUS_OK);
			if(object instanceof Map)
				object2.putAll((Map<? extends String, ? extends Object>) object);
			else {
				if(null != name)
					object2.put(name, object);
			}
			response.getWriter().write(object2.toJSONString());
		} catch (Exception e) {
			try {
				JSONObject object2 = new JSONObject();
				object2.put(KeyWords.STATUS, KeyWords.STATUS_FAIL);
				object2.put(KeyWords.MESSAGE, KeyWords.MESSAGE_DEFAULT_VALUE);
				response.getWriter().write(object2.toJSONString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
