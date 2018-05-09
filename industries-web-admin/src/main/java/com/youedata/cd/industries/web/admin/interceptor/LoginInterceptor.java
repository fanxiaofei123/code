package com.youedata.cd.industries.web.admin.interceptor;

import com.youedata.cd.base.common.constant.KeyWords;
import com.youedata.cd.common.constant.Constant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by cdyoue on 2016/7/11.
 */
public class LoginInterceptor implements HandlerInterceptor {
    // 排除字符串数组
    private String[] excludeUrls;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // 设置编码
        response.setContentType("text/html;charset=UTF-8");
        // 获取请求路径
        String requestUrl = request.getRequestURI();
        String urlString = requestUrl.substring(requestUrl.lastIndexOf("/"));
        // 匹配放行url
        for (int i = 0; excludeUrls != null && i < excludeUrls.length; i++) {
            if (urlString.equals(excludeUrls[i])) {
                return true;
            }
        }
        if(session.getAttribute(Constant.USER_KEY) == null) {
            String contextPath = request.getContextPath();
            String loginPath = contextPath + "/admin/index.do";
            request.setAttribute(KeyWords.ERROR, "请先登录或用户错误");
            response.sendRedirect(loginPath);
            return false;
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

}
