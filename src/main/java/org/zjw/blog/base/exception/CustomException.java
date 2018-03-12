package org.zjw.blog.base.exception;

@SuppressWarnings("serial")
public class CustomException extends Exception{
	//异常信息
	private String message;

	public CustomException(String message) {
		super(message);
		this.message = message;
	}
	

	public CustomException() {
		super();
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
