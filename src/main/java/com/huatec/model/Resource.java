package com.huatec.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T03_SYS_RESOURCE")
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Resource extends BaseModel {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private Integer pid;
	private String pids;
	private Integer type;
	private String url;
	private String permCode;
	private String color;
	private String icon;
	private Integer sort;
	private Integer status;
	
	/** * 放弃维护关系方*/
	@ManyToMany(mappedBy="resources")
	@JSONField(serialize = false)
	private List<Role> roles;
	
	@Transient
	private List<Resource> children;
}
