package com.cs.sigm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class CmsMissingValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1797425851915596208L;
	
	public CmsMissingValidationException() {
		super();
	}
	
	public CmsMissingValidationException(String message) {
		super(message);
	}
	
	public CmsMissingValidationException(Throwable cause) {
		super(cause);
	}
	
	public CmsMissingValidationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CmsMissingValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
