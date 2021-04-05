package com.cs.sigm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CmsPasswordRequirementsNotMet extends RuntimeException {

	private static final long serialVersionUID = 1797425851915596208L;

	public CmsPasswordRequirementsNotMet() {
		super();
	}

	public CmsPasswordRequirementsNotMet(String message) {
		super(message);
	}

	public CmsPasswordRequirementsNotMet(Throwable cause) {
		super(cause);
	}

	public CmsPasswordRequirementsNotMet(String message, Throwable cause) {
		super(message, cause);
	}

	public CmsPasswordRequirementsNotMet(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
