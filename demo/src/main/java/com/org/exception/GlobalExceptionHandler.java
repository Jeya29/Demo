package com.org.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.org.util.ResponseEntityBuilder;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
		
		List<String> details = new ArrayList<String>();
		
		details.add(ex.getMessage());
		
		
		ApiError err = new ApiError(LocalDateTime.now() ,HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND, "Resource Not Found",request.getDescription(false), details);
		
		return ResponseEntityBuilder.build(err);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		
		ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,"Malformed JSON request",request.getDescription(false),details);
		
		
		return ResponseEntityBuilder.build(err);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> details = new ArrayList<String>();
		details = ex.getBindingResult()
					.getFieldErrors()
					.stream()
					.map(error-> error.getObjectName() + ":" + error.getDefaultMessage())
					.collect(Collectors.toList());
		
		ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Validation Errors",request.getDescription(false), details);
		
		
		return ResponseEntityBuilder.build(err);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		
		details.add(String.format("Could not find the %s method for url %s", ex.getHttpMethod(), ex.getRequestURL()));
		
		ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "Method Not Found",request.getDescription(false) ,details);
		
		
		return ResponseEntityBuilder.build(err);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAll(Exception ex,WebRequest request) {
		
		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());
		
		ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Error occured",request.getDescription(false), details);
		
		return ResponseEntityBuilder.build(err);

		
		
	}
	

}
