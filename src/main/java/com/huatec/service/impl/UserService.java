package com.huatec.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatec.dao.RoleRepository;
import com.huatec.dao.UserRepository;
import com.huatec.model.User;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
}
