package com.cs.sigm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED)
public class CmsAccountLockedException extends RuntimeException {
	
	private static final long serialVersionUID = 7649623626795809130L;
	
	public CmsAccountLockedException() {
		super();
	}
	
	public CmsAccountLockedException(String message) {
		super(message);
	}
	
	public CmsAccountLockedException(Throwable cause) {
		super(cause);
	}
	
	public CmsAccountLockedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CmsAccountLockedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
