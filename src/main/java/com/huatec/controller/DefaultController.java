package com.huatec.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huatec.annotation.SysLog;
import com.huatec.model.Resource;
import com.huatec.model.Role;
import com.huatec.model.User;
import com.huatec.service.impl.UserService;
import com.huatec.utils.ResourceTreeUtils;
import com.huatec.utils.ResponseVo;
import com.huatec.utils.ResponseVoUtil;
import com.huatec.vo.SysUserVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DefaultController {
	
	@Autowired
	private UserService userService;

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
		User currentUser = userService.getCurrent();
		//重新封装导航菜单
		SysUserVo vo = new SysUserVo();
		vo.setUsername(currentUser.getUsername());
		vo.setNickname(currentUser.getNickname());
		List<Resource> menuList = new ArrayList<>();
		for(Role role : currentUser.getRoles()) {
			for(Resource res : role.getResources()) {
				if(res.getType()!=null && res.getType() ==0 ) {
					menuList.add(res);
				}
			}
		}
		List<Resource> menuTree = ResourceTreeUtils.createTree(menuList);
		vo.setResources(menuTree);
		return ResponseVoUtil.success(vo);
	}
	
	@RequestMapping("/current")
	@SysLog("获取当前用户信息")
	public ResponseVo current() {
		return ResponseVoUtil.success(userService.getCurrent());
	}
}
