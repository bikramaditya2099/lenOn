package com.bikram.lenOn.exception;

public enum ErrorHandlerEnum {

	USER_ALREADY_EXIST(2000,"User already exist","FAIL"),
	LOGIN_FAILED(2001,"Login Failed","FAIL");
	
	private int code;
	private String message;
	private String status;
	
	
	public int getCode() {
		return code;
	}


	public String getMessage() {
		return message;
	}


	public String getStatus() {
		return status;
	}


	private ErrorHandlerEnum(int code, String message, String status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
	
	
	
}
