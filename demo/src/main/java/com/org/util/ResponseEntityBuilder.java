package com.org.util;

import org.springframework.http.ResponseEntity;

import com.org.exception.ApiError;

public class ResponseEntityBuilder {
	
	public static ResponseEntity<Object> build(ApiError apiError){
		
		
		return new ResponseEntity<>(apiError,apiError.getStatus());
		
	}

}
