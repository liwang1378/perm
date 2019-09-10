package com.huatec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatec.dao.ResourceRepository;
import com.huatec.model.Resource;
import com.huatec.service.ShiroService;

@Service
@Transactional
public class ShiroServiceImpl implements ShiroService {
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Override
	public Map<String, String> getFilterChainDefinitionMap() {
		Map<String, String> filterChainMap = new LinkedHashMap<>();
		List<String[]> permsList = new ArrayList<>();
		List<String[]> anonList = new ArrayList<>();
		List<Resource> resources = resourceRepository.findAll();
		if(resources!=null) {
			for(Resource res : resources) {
				if(!"#".equals(res.getUrl()) && StringUtils.isNotBlank(res.getUrl())&&StringUtils.isNotBlank(res.getPermCode())) {
					if(res.getValid()!=null && res.getValid()==1) {
						permsList.add(new String[] {res.getUrl()+"/**","perms["+res.getPermCode()+":*]"});
					}else {
						anonList.add(new String[] {res.getUrl()+"/**","anon"});
					}
				}
			}
		}
		for(String[] strings : permsList) {
			filterChainMap.put(strings[0], strings[1]);
		}
		for(String[] strings : anonList) {
			filterChainMap.put(strings[0], strings[1]);
		}
		return filterChainMap;
	
	}

}
