package com.huatec.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.huatec.filter.PermissionAuthorizationFilter;
import com.huatec.realm.ShiroRealm;
import com.huatec.service.ShiroService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
public class ShiroConfig {

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager,
			ShiroService shiroService) {
			ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
			bean.setSecurityManager(securityManager);
			bean.setLoginUrl("/");
			
			log.info("注册权限过滤器");
			Map<String, String> filterChainDefinitionMap = shiroService.getFilterChainDefinitionMap();
			filterChainDefinitionMap.put("/index.html", "anon");
			filterChainDefinitionMap.put("/dist/**", "anon");
			filterChainDefinitionMap.put("/login", "anon");
			filterChainDefinitionMap.put("/logout", "logout");
			filterChainDefinitionMap.put("/**", "authc");//认证拦截所有请求
			log.info("{}",filterChainDefinitionMap);
			Map<String,Filter> filters = new LinkedHashMap<>();
			filters.put("perms", new PermissionAuthorizationFilter());//授权
			bean.setFilters(filters);
			bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
			return bean;
	}
	
	@Bean
	@DependsOn("credentialsMatcher")
	public SecurityManager securityManager(CredentialsMatcher credentialsMatcher) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(getShiroRealm(credentialsMatcher));
//		securityManager.setCacheManager(getCacheManager());
//		securityManager.setSessionManager(getSessionManager());
		return securityManager;
	}
	
	@Bean
	public Realm getShiroRealm(CredentialsMatcher credentialsMatcher) {
		ShiroRealm realm = new ShiroRealm();
		realm.setCredentialsMatcher(credentialsMatcher);
		return realm;
	}
	
	@Bean
	public CredentialsMatcher credentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(1);
		return credentialsMatcher;
	}
}
