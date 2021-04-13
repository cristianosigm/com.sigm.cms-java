package com.cs.sigm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class CmsMessagingUnavailableException extends RuntimeException {
	
	private static final long serialVersionUID = 1797425851915596208L;
	
	public CmsMessagingUnavailableException() {
		super();
	}
	
	public CmsMessagingUnavailableException(String message) {
		super(message);
	}
	
	public CmsMessagingUnavailableException(Throwable cause) {
		super(cause);
	}
	
	public CmsMessagingUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CmsMessagingUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
