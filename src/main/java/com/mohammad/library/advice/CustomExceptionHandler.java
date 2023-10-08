package com.mohammad.library.advice;

import com.mohammad.library.model.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<String> handleCustomException(CustomException ex) {
		// Create an appropriate HTTP response for the exception
		return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleOtherExceptions(Exception ex) {
		// Handle other exceptions, log them, and return an appropriate response
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error!");
	}
}