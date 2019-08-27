package com.huatec.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T02_SYS_ROLE")
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Role extends BaseModel {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private Integer type;
	private Integer status;
	
	@ManyToMany(mappedBy="roles")
	@JSONField(serialize = false)
//	@JsonIgnore 
	private List<User> users;
	
	//关系维护方, role => resource 
	@ManyToMany(cascade= {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinTable(name="T05_SYS_ROLE_RESOURCE",
	joinColumns=@JoinColumn(name="roleId"),
	inverseJoinColumns=@JoinColumn(name="resId"))
	private List<Resource> resources;

	@Override
	public String toString() {
		if(resources!=null && resources.size()>0) {
			return "Role [id=" + id + ", name=" + name + ", type=" + type + ", status=" + status + ", users=" + users + ", 拥有权限数量=" + resources.size() + "]";
		}
		return "Role [id=" + id + ", name=" + name + ", type=" + type + ", status=" + status + ", users=" + users + "]";
	}
	
	

}
