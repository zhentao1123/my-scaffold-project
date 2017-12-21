package com.example.demo.exception;

@SuppressWarnings("serial")
public class DBException extends Exception{
	
	private String code;
	private String message;
	
	public DBException() {}
	
	public DBException(Exception e) {
		super(e);
	}
	
	public DBException(String code, String message) {
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
