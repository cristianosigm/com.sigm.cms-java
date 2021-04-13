package com.cs.sigm.exception.handler;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cs.sigm.exception.CmsAuthenticationException;
import com.cs.sigm.exception.CmsEntryNotFoundException;
import com.cs.sigm.exception.CmsMessagingUnavailableException;
import com.cs.sigm.exception.CmsMissingValidationException;
import com.cs.sigm.exception.CmsPasswordRequirementsNotMet;
import com.cs.sigm.exception.ErrorCode;
import com.cs.sigm.exception.response.CmsResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CmsServletExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	MessageSource messageSource;
	
	@ExceptionHandler(value = {
		CmsMissingValidationException.class
	})
	protected ResponseEntity<Object> handleCmsMissingValidationException(RuntimeException exception, WebRequest request) {
		return dispatchErrorHandling(HttpStatus.UNAUTHORIZED.value(), ErrorCode.SEC_MISSING_VALIDATION, exception, request);
	}
	
	@ExceptionHandler(value = {
		CmsAuthenticationException.class
	})
	protected ResponseEntity<Object> handleCmsAuthenticationException(RuntimeException exception, WebRequest request) {
		return dispatchErrorHandling(HttpStatus.UNAUTHORIZED.value(), ErrorCode.SEC_INVALID_CREDENTIALS, exception, request);
	}
	
	@ExceptionHandler(value = {
		CmsEntryNotFoundException.class
	})
	protected ResponseEntity<Object> handleCmsEntryNotFoundException(RuntimeException exception, WebRequest request) {
		return dispatchErrorHandling(HttpStatus.NOT_FOUND.value(), ErrorCode.HAN_ENTRY_NOT_FOUND, exception, request);
	}
	
	@ExceptionHandler(value = {
		CmsMessagingUnavailableException.class
	})
	protected ResponseEntity<Object> handleCmsMessagingException(RuntimeException exception, WebRequest request) {
		return dispatchErrorHandling(HttpStatus.SERVICE_UNAVAILABLE.value(), ErrorCode.HAN_MESSAGING_UNAVAILABLE, exception, request);
	}
	
	@ExceptionHandler(value = {
		CmsPasswordRequirementsNotMet.class
	})
	protected ResponseEntity<Object> handleCmsPasswordRequirementsNotMet(RuntimeException exception, WebRequest request) {
		return dispatchErrorHandling(HttpStatus.BAD_REQUEST.value(), ErrorCode.SEC_PASSWORD_REQUIREMENTS_NOT_MET, exception, request);
	}
	
	private ResponseEntity<Object> dispatchErrorHandling(int httpStatusValue, ErrorCode errorCode, RuntimeException exception, WebRequest request) {
		log.error(" :: ExceptionHandler :: Exception message handled: {}", exception.getMessage());
		//@formatter:off
		final CmsResponseBody responseBody = CmsResponseBody.builder()
			.details(exception.getMessage())
			.error(messageSource.getMessage(errorCode.getKey(), null, Locale.getDefault()).concat("[".concat(errorCode.getCode()).concat("] ")))
			.status(httpStatusValue)
			.build();
		return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
		//@formatter:on
	}
	
}
