package com.gopher.system.exception;

import com.gopher.system.constant.CodeAndMsg;

public class BusinessRuntimeException extends RuntimeException {
	private int code;
	private String message;
	private static final long serialVersionUID = 3062632128041727450L;

	public BusinessRuntimeException(int code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	public BusinessRuntimeException(CodeAndMsg codeAndMsg) {
		super(codeAndMsg.getMsg());
		this.code = codeAndMsg.getCode();
		this.message = codeAndMsg.getMsg();
	}
	public BusinessRuntimeException(String message) {
		this.message = message;
		this.code=1001;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


}
