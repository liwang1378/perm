package com.huatec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.huatec.model.SysLog;

public interface LogRepository extends JpaRepository<SysLog, Integer>, QuerydslPredicateExecutor<SysLog> {

}
