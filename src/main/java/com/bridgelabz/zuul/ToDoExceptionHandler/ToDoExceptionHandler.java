package com.bridgelabz.zuul.ToDoExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ToDoExceptionHandler {
	@ExceptionHandler(ToDoException.class)
	public ResponseEntity<String> todoExceptionHandler(ToDoException exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
