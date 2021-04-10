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
import com.cs.sigm.exception.ErrorCode;
import com.cs.sigm.exception.response.CmsResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CmsServletExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	MessageSource messageSource;
	
	// TODO: use this as a template to implement full error-handling
	@ExceptionHandler(value = {
		CmsAuthenticationException.class
	})
	protected ResponseEntity<Object> handleCmsAuthenticationException(RuntimeException exception, WebRequest request) {
		log.info(" :: ExceptionHandler :: Exception message handled: {}", exception.getMessage());
		final CmsResponseBody responseBody = CmsResponseBody.builder().details(exception.getMessage())
			.error(messageSource.getMessage(ErrorCode.SEC_INVALID_PASSWORD_LENTGHT.getKey(), null, Locale.getDefault())).status(HttpStatus.UNAUTHORIZED.value()).build();
		return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}
	
}
