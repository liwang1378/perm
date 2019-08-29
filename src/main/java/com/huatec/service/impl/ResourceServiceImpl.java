package com.huatec.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.huatec.dao.ResourceRepository;
import com.huatec.dto.PageVo;
import com.huatec.enums.ResultEnum;
import com.huatec.exception.UserException;
import com.huatec.model.Resource;
import com.huatec.service.BaseService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class ResourceServiceImpl implements BaseService<Resource> {

	@Autowired
	private ResourceRepository resRepository;
	@Override
	public List<Resource> findAll() {
		Sort sort = new Sort(Sort.Direction.ASC,"sort");
		return resRepository.findAll(sort);
	}

	@Override
	public Resource save(Resource resource) {
		if(resource.getId()!=null) {
			resource.setUpdateTime(new Date());
		}
		return resRepository.save(resource);
	}

	@Override
	public Resource findOne(Integer id) {
		return resRepository.getOne(id);
	}

	@Override
	public void delete(Integer id) {
		Resource resource = findOne(id);
		log.info("{}",resource);
		if(resource!=null && resource.getRoles()!=null && resource.getRoles().size()>0) {
			throw new UserException(ResultEnum.CANT_DEL_RESOURCE);
		}
		resRepository.deleteById(id);
	}

	@Override
	public PageVo<Resource> query(Object query) {
		return null;
	}

}
