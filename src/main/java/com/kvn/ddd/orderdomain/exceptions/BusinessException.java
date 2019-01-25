package com.kvn.ddd.orderdomain.exceptions;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private Throwable e;

	public BusinessException() {
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message) {
		this.message = message;
	}

	public BusinessException(String message, Throwable e) {
		this.message = message;
		this.e = e;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getE() {
		return e;
	}

}
