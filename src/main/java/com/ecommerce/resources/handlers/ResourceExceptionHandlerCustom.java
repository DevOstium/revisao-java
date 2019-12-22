package com.ecommerce.resources.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.services.exceptions.ObjectNotFoundExceptionCustom;

@ControllerAdvice
public class ResourceExceptionHandlerCustom {

	@ExceptionHandler(ObjectNotFoundExceptionCustom.class)
	public ResponseEntity<StanderError> objectNotFound(ObjectNotFoundExceptionCustom e, HttpServletRequest req) {
		
		StanderError error = new StanderError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
