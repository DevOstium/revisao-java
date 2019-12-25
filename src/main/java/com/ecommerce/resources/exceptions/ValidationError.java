package com.ecommerce.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StanderError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> listErrors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	// O nome que vai para o JSON é o do get, ou seja, vai aparecer: listErrors
	public List<FieldMessage> getListErrors() {
		return listErrors;
	}

	public void addError(String fieldName, String message) {
		listErrors.add(new FieldMessage(fieldName, message));
	}

}
