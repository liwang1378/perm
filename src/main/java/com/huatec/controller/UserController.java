package com.huatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
	
	
}
