package com.huatec.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T00_SYS_LOG")
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class SysLog extends BaseModel {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private Integer userId;
	private String ip;
	private Integer ajax;
	private String uri;
	private String params;
	private String httpMethod;
	private String classMethod;
	private String response;
	private String actionName;
}
