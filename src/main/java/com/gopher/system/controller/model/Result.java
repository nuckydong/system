package com.gopher.system.controller.model;

public class Result {
    private int code;
    private String message;
    private boolean success= true;
    private Object data;
    
    public Result(){
    	this.code = 0;
    	this.message = "请求成功";
    }
    
    public Result(Object data){
    	this.code = 0;
    	this.message = "请求成功";
    	this.data = data;
    }
    
    public Result(int code,String message,boolean success){
    	this.code = code;
    	this.message = message;
    	this.success = success;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
