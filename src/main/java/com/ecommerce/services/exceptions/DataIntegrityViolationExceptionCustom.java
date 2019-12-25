package com.ecommerce.services.exceptions;

public class DataIntegrityViolationExceptionCustom extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityViolationExceptionCustom(String msg) {
		super(msg);
	}

	public DataIntegrityViolationExceptionCustom(String msg, Throwable cause) {
		super(msg, cause);
	}
}
