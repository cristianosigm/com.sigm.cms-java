package com.cs.sigm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CmsEmailAlreadyUsedException extends RuntimeException {
	
	private static final long serialVersionUID = 7649623626795809130L;
	
	public CmsEmailAlreadyUsedException() {
		super();
	}
	
	public CmsEmailAlreadyUsedException(String message) {
		super(message);
	}
	
	public CmsEmailAlreadyUsedException(Throwable cause) {
		super(cause);
	}
	
	public CmsEmailAlreadyUsedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CmsEmailAlreadyUsedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
