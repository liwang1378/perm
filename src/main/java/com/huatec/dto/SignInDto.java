package com.huatec.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class SignInDto {

	@NotBlank(message="用户名不能为空")
	private String username;
	@NotBlank(message="密码不能为空")
	@Pattern(regexp="^(\\w) {6,18}$",message="密码应为[A-Za-z0-9_]组成的6-18位字符!")
	private String password;
}
