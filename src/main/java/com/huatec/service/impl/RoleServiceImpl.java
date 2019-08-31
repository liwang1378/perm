package com.huatec.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.huatec.dao.RoleRepository;
import com.huatec.dto.PageVo;
import com.huatec.dto.QryRo;
import com.huatec.enums.ResultEnum;
import com.huatec.exception.UserException;
import com.huatec.model.Role;
import com.huatec.service.BaseService;

import lombok.extern.slf4j.Slf4j;
@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements BaseService<Role> {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role save(Role role) {
		
		if(role.getId()!=null) {
			role.setUpdateTime(new Date());
		}
		return roleRepository.saveAndFlush(role);
	}

	@Override
	public Role findOne(Integer id) {
//		return roleRepository.getOne(id);
		return roleRepository.findById(id).get();
	}

	@Override
	public void delete(Integer roleId) {
		//删除角色前判断是否已分配用户
		Role role = roleRepository.getOne(roleId);
		log.info("{}",role);
		
		log.info("roleId- {}执行删除",roleId);
		if(role.getUsers()!=null && role.getUsers().size()>0) {
			throw new UserException(ResultEnum.CANT_DEL_ROLE);
		}
		roleRepository.deleteById(roleId);
		log.info("删除角色成功");
	}

	@Override
	public PageVo<Role> query(Object query) {
		QryRo qry = (QryRo) query;
		int page = qry.getPage();
		int size = qry.getSize();
		Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
		PageVo<Role> vo = new PageVo<>();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Role> pages = roleRepository.findAll(pageable);
		vo.setTotal(pages.getTotalElements());
		vo.setPage(pages.getTotalPages());
		vo.setData(pages.getContent());
		return vo;
	}

	public Object update(Role role) {
		role.setUpdateTime(new Date());
		return roleRepository.save(role);
	}

}
