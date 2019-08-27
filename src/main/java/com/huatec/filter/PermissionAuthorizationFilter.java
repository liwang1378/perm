package com.huatec.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.alibaba.fastjson.JSON;
import com.huatec.enums.ResultEnum;
import com.huatec.utils.ResponseVoUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class PermissionAuthorizationFilter extends AccessControlFilter  {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		log.info("{}是否允许-权限授权过滤器执行入口",mappedValue);
		if(null != mappedValue) {
			String[] value = (String[]) mappedValue;
			log.info("{}",value);
			for(String perm : value) {
				if(perm==null || "".equals(perm)) {
					continue;
				}
				if(subject.isPermitted(perm)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		log.info("访问拒绝方法入口");
		Subject subject = getSubject(request, response);
		saveRequest(request);
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Content-Type", "application/json;charset=utf-8");
		if(null == subject.getPrincipal()) {//未登录
			res.getWriter().write(JSON.toJSONString(ResponseVoUtil.error(ResultEnum.NOT_SING_IN.getCode(), ResultEnum.NOT_SING_IN.getMsg())));
		}else {
			log.info("{}",subject.getPrincipal());
			res.getWriter().write(JSON.toJSONString(ResponseVoUtil.error(ResultEnum.NOT_PERM.getCode(), ResultEnum.NOT_PERM.getMsg())));
		}
		return false;
	}

}
