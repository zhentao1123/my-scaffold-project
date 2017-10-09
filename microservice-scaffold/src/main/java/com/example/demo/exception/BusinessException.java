package com.example.demo.exception;

@SuppressWarnings("serial")
public class BusinessException extends Exception {
	
    public BusinessException(String message) {
        super(message);
    }

}
