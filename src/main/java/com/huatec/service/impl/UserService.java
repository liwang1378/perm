package com.huatec.service.impl;

import java.util.ArrayList;
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
import com.huatec.dao.UserRepository;
import com.huatec.dto.PageVo;
import com.huatec.dto.QryRo;
import com.huatec.dto.UserRole;
import com.huatec.model.Role;
import com.huatec.model.User;
import com.huatec.utils.Constant;
import com.huatec.utils.PasswordUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User save(User user) {
		if(user.getId()==null) {
			//新增用户,默认密码 123456
			user.setSalt(PasswordUtil.getRandomString(6));
			user.setPassword(PasswordUtil.encryptMD5(Constant.DEFAULT_PASSWORD,user.getSalt(),Constant.HASH_TIMES));
		}else {
			user.setUpdateTime(new Date());
		}
		
		return userRepository.save(user);
	}
	
	public User findOne(Integer id) {
		return userRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	public Object bindRoles(UserRole userRole) {
		log.info("{}",userRole);
		User user = userRepository.getOne(userRole.getUserId());
		List<Role> roles = roleRepository.findAllById(userRole.getRoleIds());
		user.setRoles(roles);
		return userRepository.saveAndFlush(user);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public PageVo<User> queryUser(QryRo query) {
		if(query == null) {
			query = new QryRo();
		}
		Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
		Pageable pageable = PageRequest.of(query.getPage(), query.getSize(),sort);
		Page<User> page = userRepository.findAll(pageable);
		PageVo<User> vo = new PageVo<>();
		vo.setTotal(page.getTotalElements());
		vo.setPage(page.getTotalPages());
		List<User> userList = new ArrayList<User>();
		page.iterator().forEachRemaining(user -> {
			userList.add(user);
		});
		vo.setData(userList);
		return vo;
	}
}
