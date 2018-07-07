package com.bikram.lenOn.exception;

public class LenOnException extends Exception{
	
	private int code;
	private String message;
	private String status;
	
	public LenOnException(ErrorHandlerEnum handlerEnum)
	{
		this.code=handlerEnum.getCode();
		this.message=handlerEnum.getMessage();
		this.status=handlerEnum.getStatus();
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "{ \"code\":" + code + ", \"message\":" +"\""+ message +"\""+ ", \"status\":" +"\""+ status + "\" }";
	}
	
	
	
	

}
