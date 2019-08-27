package com.huatec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.huatec.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Integer>, QuerydslPredicateExecutor<Resource> {

}
