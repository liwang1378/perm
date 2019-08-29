package com.huatec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huatec.annotation.SysLog;
import com.huatec.model.Resource;
import com.huatec.service.impl.ResourceServiceImpl;
import com.huatec.utils.ResourceTreeUtils;
import com.huatec.utils.ResponseVo;
import com.huatec.utils.ResponseVoUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/res")
public class ResourceController {

	@Autowired
	private ResourceServiceImpl resService;
	
	@RequestMapping("/list")
	@SysLog("资源列表")
	public ResponseVo resList() {
		List<Resource> resList = resService.findAll();
		//重新组装成树形数据结构
		return ResponseVoUtil.success(ResourceTreeUtils.createTree(resList));
	}
	
	//添加资源(有res(id)则更新)
	@PostMapping("/addOrUpdate")
	@SysLog("资源新增或更新")
	public ResponseVo addOrupdate(@RequestBody(required=false) Resource res) {
		log.info("资源详情 - {}",res);
		return ResponseVoUtil.success(resService.save(res));
	}
	
	@GetMapping("/delete/{id}")
	@SysLog("资源删除")
	public ResponseVo delete(@PathVariable Integer id) {
		resService.delete(id);
		return ResponseVoUtil.success();
	}
}
