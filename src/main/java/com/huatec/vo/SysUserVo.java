package com.huatec.vo;

import java.util.Date;
import java.util.List;

import com.huatec.model.Resource;
import com.huatec.model.Role;

import lombok.Data;

@Data
public class SysUserVo {

	private Integer id;
	private String username;
	private String nickname;
	private Integer status;
	private List<Role> roles;
	private List<Resource> resources;
	private Date addTime;
	private Date updateTime;
}
