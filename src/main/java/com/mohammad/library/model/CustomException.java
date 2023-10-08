package com.mohammad.library.model;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {

	HttpStatus httpStatus;
	public CustomException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
