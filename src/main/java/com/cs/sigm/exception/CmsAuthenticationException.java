package com.cs.sigm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class CmsAuthenticationException extends RuntimeException {
	
	private static final long serialVersionUID = 7649623626795809130L;
	
	public CmsAuthenticationException() {
		super();
	}
	
	public CmsAuthenticationException(String message) {
		super(message);
	}
	
	public CmsAuthenticationException(Throwable cause) {
		super(cause);
	}
	
	public CmsAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CmsAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
