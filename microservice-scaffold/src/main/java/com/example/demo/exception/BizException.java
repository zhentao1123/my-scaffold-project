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
	
	public BizException(BizErrEnum bizErrEnum, Exception e) {
		super(e);
		this.code = bizErrEnum.getCode();
		this.message = bizErrEnum.getMessage();
	}
	
	public BizException(String code, String message, Exception e) {
		super(e);
		this.code = code;
		this.message = message;
	}
	
	public BizException(BizErrEnum bizErrEnum) {
		this.code = bizErrEnum.getCode();
		this.message = bizErrEnum.getMessage();
	}

	public static BizException getCommException() {
		return new BizException(BizException.BizErrEnum.COMM_ERR);
	}
	
	public static BizException getParamException() {
		return new BizException(BizException.BizErrEnum.PARAM_ERR);
	}
	
	public static BizException getParamException(String message) {
		return new BizException(BizException.BizErrEnum.PARAM_ERR.code, message);
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
	
	//---------------------------------------------------------
	
	public static enum BizErrEnum{
		COMM_ERR("1", "操作失败"),
		
		PARAM_ERR("100", "参数错误"), 
		;
		
		private String code;
		private String message;
		
		private BizErrEnum(String code, String message) {
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
}
