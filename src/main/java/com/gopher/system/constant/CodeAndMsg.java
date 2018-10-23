package com.gopher.system.constant;

public enum CodeAndMsg {
	//系统通用
	REQUEST_SUCCESS(0,"REQUEST_SUCCESS"),
	SYSTEM_EXCEPTION(-1,"SYSTEM_EXCEPTION"),
	NEED_LOGIN(1,"NEED_LOGIN"),
	PARAM_NOT_NULL(1001, "PARAM_NOT_NULL"),
	INVALID_PARAM(1002,"INVALID_PARAM"),
	CANNOT_FIND_IN_DB(1003,"CANNOT_FIND_IN_DB"),
	FORBIDDEN(1004,"HAD_NOT_AUTH"),
	NOT_CUSTOMER(1005,"MUST_BE_CUSTOMER"),
	HAD_NOT_AUTH(1006,"HAD_NOT_AUTH");
	
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
