package com.huatec.exception;

import com.huatec.enums.ResultEnum;

import lombok.Getter;
import lombok.Setter;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private Integer code;
	
	public UserException(ResultEnum resultEnum) {
		super(resultEnum.getMsg());
		this.code = resultEnum.getCode();
	}
}
