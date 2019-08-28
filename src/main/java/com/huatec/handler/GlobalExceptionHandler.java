package com.huatec.handler;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatec.enums.ResultEnum;
import com.huatec.exception.UserException;
import com.huatec.utils.ResponseVo;
import com.huatec.utils.ResponseVoUtil;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public ResponseVo handle(Exception e) {
		String msg = "【用户或密码错误】";
		if(e instanceof UserException) {
			UserException userException = (UserException) e;
			return ResponseVoUtil.error(userException.getCode(), e.getMessage());
		}if(e instanceof UnknownAccountException) {
			return ResponseVoUtil.error(ResultEnum.UNKNOWNACCOUNT.getCode(), msg);
		}if(e instanceof IncorrectCredentialsException) {
			return ResponseVoUtil.error(ResultEnum.INCORRECTCREDENTIALS.getCode(), msg);
		}if(e instanceof DisabledAccountException) {
			return ResponseVoUtil.error(ResultEnum.LOCKED.getCode(), "【用户已禁用】");
		}else {
			e.printStackTrace();
			log.error("【系统异常】 - {}" , e.getLocalizedMessage());
			return ResponseVoUtil.error(-1, e.getMessage());
		}
	}

}
