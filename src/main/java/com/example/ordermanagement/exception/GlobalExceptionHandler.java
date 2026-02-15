package com.example.ordermanagement.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(OutOfStockException.class)
	public ResponseEntity<Map<String,Object>> handleOutOfStockException(OutOfStockException ex){
		Map<String,Object> error=new HashMap<>();
		error.put("message", ex.getMessage());
		error.put("timestamp", LocalDateTime.now().toString());
		return ResponseEntity.status(HttpStatus.OK).body(error);
	}
}
