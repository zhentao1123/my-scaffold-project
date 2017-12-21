package com.example.demo.exception;

@SuppressWarnings("serial")
public class BizException extends Exception{
	
	private String code;
	private String message;
	
	public BizException() {}
	
	public BizException(Exception e) {
		super(e);
	}
	
	public BizException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
