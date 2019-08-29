package com.huatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huatec.annotation.SysLog;
import com.huatec.dto.PageVo;
import com.huatec.dto.QryRo;
import com.huatec.model.Role;
import com.huatec.service.impl.RoleServiceImpl;
import com.huatec.utils.ResponseVo;
import com.huatec.utils.ResponseVoUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {
	
	@Autowired
	private RoleServiceImpl roleService;
	
	@GetMapping("/findAll")
	@SysLog("角色列表")
	public ResponseVo findAll() {
		return ResponseVoUtil.success(roleService.findAll());
	}
	
	//添加角色
	@RequestMapping("/addOrUpdate")
	@SysLog("角色新增或更新")
	public ResponseVo addOrUpdate(@RequestBody @Validated Role role) {
		return ResponseVoUtil.success(roleService.save(role));
	}
	
	@RequestMapping("/delete/{id}")
	@SysLog("角色删除")
	public ResponseVo delete(@PathVariable Integer id) {
		roleService.delete(id);
		return ResponseVoUtil.success();
	}
	
	@RequestMapping("/list")
	@SysLog("角色分页列表")
	public ResponseVo list(QryRo ro) {
		log.info("{}",ro);
		PageVo<Role> pageVo = roleService.query(ro);
		return ResponseVoUtil.success(pageVo);
	}
	
	//状态 0 未激活 1 正常 2 锁定
	@RequestMapping("/alterStatus")
	@SysLog("角色状态变更")
	public ResponseVo alterStatus(@RequestBody Role role) {
		return ResponseVoUtil.success(roleService.update(role));
	}

}
