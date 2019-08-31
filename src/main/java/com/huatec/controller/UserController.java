package com.huatec.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.huatec.annotation.SysLog;
import com.huatec.dto.QryRo;
import com.huatec.dto.UserRole;
import com.huatec.enums.ResultEnum;
import com.huatec.model.User;
import com.huatec.service.impl.UserService;
import com.huatec.utils.PasswordUtil;
import com.huatec.utils.ResponseVo;
import com.huatec.utils.ResponseVoUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/list",produces = "application/json;charset=utf-8" )
	@SysLog("用户列表")
	public ResponseVo userList(@RequestBody(required=false)QryRo query) {
//		return ResponseVoUtil.success(userService.findAll());
		log.info("根据条件分页查询",query);
		return ResponseVoUtil.success(userService.queryUser(query));
	}
	
	//新增或更新用户
	@RequestMapping("/addUser")
	@SysLog("用户新增或更新")
	public ResponseVo userAdd(@Valid @RequestBody User user,BindingResult result) {
		log.info("{}",user);
		if(result.hasErrors()) {
			return ResponseVoUtil.error(ResultEnum.INVALIDITY.getCode(), 
//					ResultEnum.INVALIDITY.getMsg());
					result.getFieldError().getDefaultMessage());
		}
		return ResponseVoUtil.success(userService.save(user));
	}
	
	@PostMapping("/alterStatus")
	@SysLog("用户状态变更")
	public ResponseVo alterStatus(@RequestBody User user) {
		User u = userService.findOne(user.getId());
		u.setStatus(user.getStatus());
		return ResponseVoUtil.success(userService.save(u));
	}
	
	@GetMapping("/get/{id}")
	@SysLog("获取指定用户")
	public ResponseVo get(@PathVariable("id")Integer id) {
		User user = userService.findOne(id);
		return ResponseVoUtil.success(user);
	}
	
	//删除
	@RequestMapping("/delete/{id}")
	@SysLog("用户删除")
	public ResponseVo delete(@PathVariable("id")Integer id) {
		userService.delete(id);
		return ResponseVoUtil.success();
	}
	
	@PostMapping("/bindRoles")
	@SysLog("用户绑定角色")
	public ResponseVo bindRoles(@RequestBody UserRole userRole) {
		return ResponseVoUtil.success(userService.bindRoles(userRole));
	}
}
	
	

