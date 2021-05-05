package com.cs.sigm.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	//@formatter:off
	SEC_INVALID_CREDENTIALS(			"SEC-001", "error.security.invalidCredentials"),
	SEC_MISSING_VALIDATION(				"SEC-002", "error.security.missingValidation"),
	SEC_PASSWORD_REQUIREMENTS_NOT_MET(	"SEC-003", "error.security.passwordRequirementsNotMet"),
	SEC_ACCOUNT_LOCKED(					"SEC-004", "error.security.accountLocked"),
	SEC_EMAIL_ALREADY_USED(				"SEC-005", "error.security.emailexists"),
	HAN_ENTRY_NOT_FOUND(				"HAN-001", "error.handling.entryNotFound"),
	HAN_MESSAGING_UNAVAILABLE(			"HAN-002", "error.handling.messagingUnavailable"),
	HAN_UNAMEPPED(						"HAN-999", "error.unmapped");
	//@formatter:on
	
	private String code;
	
	private String key;
	
	private ErrorCode(String code, String key) {
		this.code = code;
		this.key = key;
	}
	
}
