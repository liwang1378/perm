package com.huatec.util;

import org.junit.Test;

import com.huatec.utils.PasswordUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordTest {

	@Test
	public void test() {
		int length = 6;
		String password = "123456";
		String salt = PasswordUtil.getRandomString(length);
		int times = 1;
		log.info("{}",salt);
		
		String encryptPwd = PasswordUtil.encryptMD5(password, salt, times);
		log.info("{}",encryptPwd);
		
		String md5 = PasswordUtil.generate(password);
		System.out.println(PasswordUtil.verify(password,md5));
	}

}
