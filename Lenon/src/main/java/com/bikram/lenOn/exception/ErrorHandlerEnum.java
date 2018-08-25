package com.bikram.lenOn.exception;

public enum ErrorHandlerEnum {

	USER_ALREADY_EXIST(2000,"User already exist","FAIL"),
	LOGIN_FAILED(2001,"Login Failed","FAIL"),
	LOGIN_SUCCESS(2002,"Login Success","SUCCESS"),
	ERROR(2003,"Something went wrong","FAIL"),
	CUSTOMER_ALREADY_EXISTS(2004,"Customer Already Exists","FAIL"),
	NO_SUCH_USER(2005,"No Such User Exists","FAIL"),
	SUCCESSFULLY_ADDED_DEBIT(2006,"Debit amount added successfully","SUCCESS"),
	SUCCESSFULLY_ADDED_CREDIT(2007,"Credit amount added successfully","SUCCESS"),
	CUSTOMER_SUCCESFULLY_ADDED(2008,"Customer Successfully ADDED","SUCCESS"),
	INVALID_USER(2009,"Invalid User","FAIL"),
	DEBIT_FAILURE(2010,"CANT DEBIT MORE THAN CREDIT AMOUNT","FAIL"),
	SIGNUP_SUCCESS(2011,"SignUp Success","SUCCESS"),
	;
	
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
