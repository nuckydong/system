package com.gopher.system.constant;

public enum CodeAndMsg {
	REQUEST_SUCCESS(0,"请求成功"),
	SYSTEM_EXCEPTION(-1,"系统异常"),
	NEED_LOGIN(1,"没有登录,请登录"),
	PARAM_NOT_NULL(1001, "参数不能为空"),
	INVALID_PARAM(1002,"非法的参数"),
	CANNOT_FIND_IN_DB(1003,"数据库找不到记录"),
	FORBIDDEN(1004,"没有权限");
	
	private String msg;
	private int code;
	private CodeAndMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getMsg(int code) {
		for (CodeAndMsg c : CodeAndMsg.values()) {
			if (c.getCode() == code) {
				return c.msg;
			}
		}
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
