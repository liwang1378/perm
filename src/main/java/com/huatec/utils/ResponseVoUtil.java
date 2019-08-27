package com.huatec.utils;

public class ResponseVoUtil {
	
	public static ResponseVo success(Object object) {
		ResponseVo vo = new ResponseVo();
		vo.setCode(200);
		vo.setMsg("成功");
		vo.setData(object);
		vo.setSuccess(true);
		return vo;
	}
	
	public static ResponseVo success() {
		return success(null);
	}
	
	public static ResponseVo error(Integer code,String msg) {
		ResponseVo vo = new ResponseVo();
		vo.setCode(code);
		vo.setMsg(msg);
		vo.setSuccess(false);
		return vo;
	}
}
