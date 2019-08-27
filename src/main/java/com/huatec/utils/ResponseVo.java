package com.huatec.utils;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private boolean success;
	private Integer code;
	private String msg;
	private Object data;
}
