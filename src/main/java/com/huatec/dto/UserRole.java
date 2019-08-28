package com.huatec.dto;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class UserRole {
	private Integer id;
	private Integer userId;
	private Integer roleId;
//	@Transient
	private List<Integer> roleIds;
}
