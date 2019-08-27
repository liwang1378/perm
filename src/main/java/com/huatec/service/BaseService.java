package com.huatec.service;

import java.util.List;

public interface BaseService<T> {

	List<T> findAll();
	T save(T entity);
	T findOne(Integer id);
	void delete(Integer id);
//	PageVo<T> query(Object query);
}
