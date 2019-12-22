package com.ecommerce.services.exceptions;

public class ObjectNotFoundExceptionCustom extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundExceptionCustom(String msg) {
		super(msg);
	}

	public ObjectNotFoundExceptionCustom(String msg, Throwable cause) {
		super(msg, cause);
	}
}
