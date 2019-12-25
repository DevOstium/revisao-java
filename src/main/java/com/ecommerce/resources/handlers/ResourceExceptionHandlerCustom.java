package com.ecommerce.resources.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.resources.exceptions.StanderError;
import com.ecommerce.resources.exceptions.ValidationError;
import com.ecommerce.services.exceptions.DataIntegrityViolationExceptionCustom;
import com.ecommerce.services.exceptions.ObjectNotFoundExceptionCustom;

@ControllerAdvice
public class ResourceExceptionHandlerCustom {

	@ExceptionHandler(ObjectNotFoundExceptionCustom.class)
	public ResponseEntity<StanderError> objectNotFound(ObjectNotFoundExceptionCustom e, HttpServletRequest req) {

		StanderError error = new StanderError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityViolationExceptionCustom.class)
	public ResponseEntity<StanderError> objectDataINtegrity(DataIntegrityViolationExceptionCustom e, HttpServletRequest req) {

		StanderError error = new StanderError(HttpStatus.BAD_REQUEST.value(), e.getMessage() , System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StanderError> validation(MethodArgumentNotValidException e, HttpServletRequest req) {

		ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Error de Validação", System.currentTimeMillis());

		for (FieldError x : e.getBindingResult().getFieldErrors() ) {
			error.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
