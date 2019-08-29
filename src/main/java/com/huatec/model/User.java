package com.huatec.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T01_SYS_USER")
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User extends BaseModel {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message="用户账号必填")
	private String username;
	@NotBlank(message="用户昵称必填")
	private String nickname;
	//未生效
	@Length(min=6,max=20,message="密码长度需要在{min}和{max}之间")
	private String password;
	@NotBlank(message="盐必填")
	private String salt;
	private String pids;
	private Integer deptId;
	private Integer status;
	
	@ManyToMany(fetch=FetchType.EAGER,cascade= {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinTable(name="T04_SYS_USER_ROLE",
	joinColumns=@JoinColumn(name="userId"),
	inverseJoinColumns=@JoinColumn(name="roleId"))
	private List<Role> roles;

	@Override
	public String toString() {
		if(roles!=null && roles.size()>0) {
			return "User [id=" + id + ", username=" + username + ", nickname=" + nickname + ", password=" + password
					+ ", salt=" + salt + ", pids=" + pids + ", deptId=" + deptId + ", status=" + status + ", 角色集合数量=" + roles.size()
					+ "]";
		}
		return "User [id=" + id + ", username=" + username + ", nickname=" + nickname + ", password=" + password
				+ ", salt=" + salt + ", pids=" + pids + ", deptId=" + deptId + ", status=" + status + "]";
	}

}
