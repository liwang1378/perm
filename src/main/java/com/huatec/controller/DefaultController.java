package com.huatec.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huatec.annotation.SysLog;
import com.huatec.model.User;
import com.huatec.utils.ResponseVo;
import com.huatec.utils.ResponseVoUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DefaultController {

	@RequestMapping("/login")
	@SysLog("登录")
	public ResponseVo login(@RequestBody User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
//		subject.getSession().setTimeout(5000);
		log.info("{}登录成功",username);
		return ResponseVoUtil.success();
	}
}
