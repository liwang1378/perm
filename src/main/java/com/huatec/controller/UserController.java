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

import com.huatec.dto.UserRole;
import com.huatec.enums.ResultEnum;
import com.huatec.model.User;
import com.huatec.service.impl.UserService;
import com.huatec.utils.ResponseVo;
import com.huatec.utils.ResponseVoUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="list",produces = "application/json;charset=utf-8" )
	public ResponseVo userList() {
		return ResponseVoUtil.success(userService.findAll());
//		String json =JSON.toJSONString(userService.findAll(), SerializerFeature.DisableCircularReferenceDetect);
//		return ResponseVoUtil.success(JSON.parseObject(json));
	}
	
	//新增或更新用户
	@RequestMapping("add")
	public ResponseVo userAdd(@Valid @RequestBody User user,BindingResult result) {
		log.info("{}",user);
		if(result.hasErrors()) {
			return ResponseVoUtil.error(ResultEnum.INVALIDITY.getCode(), 
					result.getFieldError().getDefaultMessage());
		}
		return ResponseVoUtil.success(userService.save(user));
	}
	
	@PostMapping("/alterStatus")
	public ResponseVo alterStatus(@RequestBody User user) {
		return ResponseVoUtil.success(userService.save(user));
	}
	
	@GetMapping("/get/{id}")
	public ResponseVo get(@PathVariable("id")Integer id) {
		User user = userService.findOne(id);
		return ResponseVoUtil.success(user);
	}
	
	//删除
	@RequestMapping("/delete/{id}")
	public ResponseVo delete(@PathVariable("id")Integer id) {
		userService.delete(id);
		return ResponseVoUtil.success();
	}
	
	@PostMapping("/bindRoles")
	public ResponseVo bindRoles(@RequestBody UserRole userRole) {
		return ResponseVoUtil.success(userService.bindRoles(userRole));
	}
}
	
	

