package com.huatec.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.huatec.model.Role;
import com.huatec.model.User;

import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CascadeTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private RoleRepository roleRepository;
	@Autowired
	private ResourceRepository resourceRepository;

	@Test
	public void init() {
		User user = new User();
		user.setUsername("zs");
		user.setNickname("张三");
		user.setPassword(UUID.randomUUID().toString().substring(0, 6));
		user.setSalt(UUID.randomUUID().toString().substring(0, 6));
		userRepository.saveAndFlush(user);
		User user2 = new User();
		user2.setUsername("wu");
		user2.setNickname("王五");
		user2.setPassword(UUID.randomUUID().toString().substring(0, 6));
		user2.setSalt(UUID.randomUUID().toString().substring(0, 6));
		userRepository.save(user2);
		
		Role role1 = new Role();
		Role role2 = new Role();
		Role role3 = new Role();
		role1.setName("管理员");
		role2.setName("总经理");
		role3.setName("部门经理");
		roleRepository.save(role1);
		roleRepository.save(role2);
		roleRepository.save(role3);
	}
	
	@Test
	public void clear() {
		userRepository.deleteAllInBatch();
		roleRepository.deleteAllInBatch();
		resourceRepository.deleteAllInBatch();
	}
	
	@Test
//	@Transactional
	public void cascadeUpdate() {
		List<Role> roles = roleRepository.findAll();
		
		User user2 = userRepository.findByUsername("wu");
//		user2.getRoles().clear();
		
		Role role = roleRepository.findByName("总经理").get();
		List<Role> roles2 = new ArrayList<Role>();
		roles2.add(role);
		user2.setRoles(roles2);
		user2.setAddTime(new Date());
		System.out.println(user2);
		log.info("用户重新角色分配 - {}",userRepository.saveAndFlush(user2));
		
		User user = userRepository.findByUsername("wu");
		roles.remove(0);
		user.setRoles(roles);
		user.setAddBy("admin");
		user.setAddTime(new Date());
		log.info("用户角色分配 - {}",userRepository.save(user));
	}
	
	//连带删除关联关系
	@Test
	public void cascadeDelete() {
		User user = userRepository.findByUsername("wu");
		userRepository.delete(user);
	}
	
	//已经分配给用户的角色,不能删除
	@Test
	public void cantDelete() {
		try{
			roleRepository.deleteById(30);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
