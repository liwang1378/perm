package com.huatec.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.huatec.model.Resource;
import com.huatec.model.Role;
import com.huatec.model.User;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserDaoTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ResourceRepository resourceRepository;

	@Before
	public void setUp() throws Exception {
		User user = new User();
		user.setUsername("zs");
		user.setNickname("张三");
		user.setPassword(UUID.randomUUID().toString().substring(0, 6));
		user.setSalt(UUID.randomUUID().toString().substring(0, 6));
//		log.info("{}",userRepository.save(user));
	}

	@After
	public void tearDown() throws Exception {
//		userRepository.deleteAll();
		log.warn("清空用户表数据");
	}
	
	@Test
	public void testRole() {
		Role role = new Role();
		role.setName("测试");
		log.info("{}",roleRepository.save(role));
	}

	@Test
//	@Transactional
	public void test() {
		Role role1 = new Role();
		Role role2 = new Role();
		role1.setName("管理员");
		role2.setName("经理");
		List<Role> roles = new ArrayList<>();
		roles.add(role1);
		roles.add(role2);
		User user = userRepository.findByUsername("zs");
		user.setRoles(roles);
		log.info("{}",userRepository.save(user));
	}
	
	@Test
	public void testResource() {
		Role role = new Role();
		role.setName("管理员");
		Resource res1 = new Resource();
		res1.setName("用户新增");
		res1.setUrl("/user/add");
		res1.setPermCode("user:add");
		Resource res2 = new Resource();
		res2.setName("用户删除");
		res2.setUrl("/user/del");
		res2.setPermCode("user:del");
		
		List<Resource> resources = new ArrayList<>();
		resources.add(res1);
		resources.add(res2);
		role.setResources(resources);
		log.info("{}",roleRepository.save(role));
	}
	
	@Test
	@Transactional
	public void testFindRole() {
		log.info("{}",roleRepository.findById(14).get());
	}
	
	@Test
	@Transactional
	public void testFindUser() {
		log.info("{}",userRepository.findByUsername("zs"));
	}

}
