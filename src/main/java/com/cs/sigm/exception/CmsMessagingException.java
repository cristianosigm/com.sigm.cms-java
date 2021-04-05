package com.cs.sigm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class CmsMessagingException extends RuntimeException {
	
	private static final long serialVersionUID = 1797425851915596208L;
	
	public CmsMessagingException() {
		super();
	}
	
	public CmsMessagingException(String message) {
		super(message);
	}
	
	public CmsMessagingException(Throwable cause) {
		super(cause);
	}
	
	public CmsMessagingException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CmsMessagingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
