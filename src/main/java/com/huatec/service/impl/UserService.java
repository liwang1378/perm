package com.huatec.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatec.dao.RoleRepository;
import com.huatec.dao.UserRepository;
import com.huatec.dto.UserRole;
import com.huatec.model.Role;
import com.huatec.model.User;

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
}
