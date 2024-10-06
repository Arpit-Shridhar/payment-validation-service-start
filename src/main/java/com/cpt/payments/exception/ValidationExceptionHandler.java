package com.cpt.payments.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.pojo.ErrorResponse;

@ControllerAdvice
public class ValidationExceptionHandler {


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
    	
    	ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(),ex.getErrorMessage());
    	
    	System.out.println("ValidationException handled | errorResponse:"+ errorResponse);
    	
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
	
}
