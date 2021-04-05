package com.cs.sigm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CmsEntryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3707678340062548307L;

	public CmsEntryNotFoundException() {
		super();
	}

	public CmsEntryNotFoundException(String message) {
		super(message);
	}

	public CmsEntryNotFoundException(Throwable cause) {
		super(cause);
	}

	public CmsEntryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CmsEntryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
