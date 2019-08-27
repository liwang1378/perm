package com.huatec.enums;

import lombok.Getter;

public enum ResultEnum {
	NOT_SING_IN(-2,"用户未登录或身份异常"),
	NOT_PERM(-3,"用户无此权限"),
	UNKNOWN_ERROR(-1, "未知错误"),
	PENDING(600, "未激活"),
	UNKNOWNACCOUNT(601, "未知账户"),
	INCORRECTCREDENTIALS(602, "密码错误"),
	LOCKED(603, "锁定");
	@Getter
	private Integer code;
	@Getter
	private String msg;
	
	private ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
