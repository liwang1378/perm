package com.huatec.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.huatec.dao.LogRepository;
import com.huatec.dto.SignInDto;
import com.huatec.model.SysLog;
import com.huatec.model.User;
import com.huatec.utils.Tools;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SysLogAspect {

	@Autowired
	private LogRepository logRepository;
	
	@Pointcut("@annotation(com.huatec.annotation.SysLog)")
	public void log() {}
	
	@AfterReturning(returning="object",pointcut="log()")
	public void after(JoinPoint joinPoint,Object object) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Subject subject = SecurityUtils.getSubject();
		SysLog sysLog = new SysLog();
		if(subject.getPrincipals()!=null) {
			User user = (User) subject.getPrincipals().getPrimaryPrincipal();
			sysLog.setUsername(user.getUsername());
			sysLog.setUserId(user.getId());
		}else {
			sysLog.setUsername("匿名访客");
			sysLog.setUserId(0);
		}
		//获取动作Action
		sysLog.setActionName(getMethodSysLogsAnnotationValue(joinPoint));
		sysLog.setIp(Tools.getClientIp(request));
		sysLog.setUri(request.getRequestURI());
		sysLog.setAjax(Tools.ajax(request)?1:0);
		String params = this.paramFilter(joinPoint.getArgs());
		sysLog.setParams(params);
		sysLog.setHttpMethod(request.getMethod());
		sysLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName()+"()");
		sysLog.setResponse(JSON.toJSONString(object));
		logRepository.save(sysLog);
	}

	//判断是否包含有密码敏感数据
	private String paramFilter(Object[] args) {
		log.info("方法参数 - {}",args);
		final String filterString = "******";
		if(args.length>0) {
			for(int i=0;i<args.length;i++) {
				if(args[i] instanceof SignInDto) {
					SignInDto sign = (SignInDto) args[i];
					sign.setPassword(filterString);
					args[i] = sign;
				}
			}
		}
		return JSON.toJSONString(args);
	}

	private String getMethodSysLogsAnnotationValue(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		if(method.isAnnotationPresent(com.huatec.annotation.SysLog.class)) {
			//获取方法上注解中的内容
			com.huatec.annotation.SysLog sysLog = method.getAnnotation(com.huatec.annotation.SysLog.class);
			return sysLog.value();
		}
		return "未知";
	}
}
