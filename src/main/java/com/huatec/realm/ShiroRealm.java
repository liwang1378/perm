package com.huatec.realm;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.huatec.model.Resource;
import com.huatec.model.Role;
import com.huatec.model.User;
import com.huatec.service.impl.ResourceServiceImpl;
import com.huatec.service.impl.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShiroRealm extends AuthorizingRealm{
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Autowired
	private UserService userService;
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		String username = ((User)principals.getPrimaryPrincipal()).getUsername();
		Set<String> permissions = getPermissionsByUsername(username);
		log.info("{}拥有的权限集合 - {}",username,permissions);
		info.addStringPermissions(permissions);
		return info;
	}

	private Set<String> getPermissionsByUsername(String username) {
		User user = userService.findByUsername(username);
		log.info("{}",user);
		Set<String> perms = new LinkedHashSet<>();
		if(user.getRoles()!=null && user.getRoles().size()>0) {
			for(Role role : user.getRoles()) {
				if(role.getResources()!=null && role.getResources().size()>0) {
					for(Resource resource : role.getResources()) {
						if(!"#".equals(resource.getUrl())&& !"#".equals(resource.getPermCode())) {
							perms.add(resource.getPermCode());
						}
					}
				}
			}
		}
		log.info("权限集合 - {}",perms);
		return perms;
	}

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		User user = userService.findByUsername(username);
		if(user == null) {
			return null;
		}
		if(user.getStatus()==2) {
			throw new DisabledAccountException();
		}
		String password = user.getPassword();
		String salt = user.getSalt();
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
				user,password,ByteSource.Util.bytes(salt),getName());
		return info;
	}

}
