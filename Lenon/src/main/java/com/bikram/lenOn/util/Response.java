package com.bikram.lenOn.util;

import com.bikram.lenOn.exception.ErrorHandlerEnum;

public class Response {

	private int code;
	private String message;
	private String status;
	private Object data;
	public Response(ErrorHandlerEnum resp,Object data)
	{
		this.code=resp.getCode();
		this.message=resp.getMessage();
		this.status=resp.getStatus();
		this.data=data;
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
	public Object getData() {
		return data;
	}
	
	
}
