package com.cs.sigm.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	
	SEC_INVALID_PASSWORD_LENTGHT("SEC-001", "error.security.invalidPasswordLength");
	
	private String code;
	
	private String key;
	
	private ErrorCode(String code, String key) {
		this.code = code;
		this.key = key;
	}
	
}
