package com.cs.sigm.security.utils;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.exception.CmsPasswordRequirementsNotMet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PasswordValidator {
	
	private static final String PATTERN_LOWER = "(?=.*[a-z])";
	private static final String PATTERN_UPPER = "(?=.*[A-Z])";
	private static final String PATTERN_NUMBER = "(?=.*[0-9])";
	private static final String PATTERN_SPECIAL = "(?=.*[@#$%^&+=])";
	
	private Pattern pattern = null;
	
	@Autowired
	private CmsConfig config;
	
	public void validate(String password) {
		if (this.pattern == null) {
			//@formatter:off
			this.pattern = Pattern.compile("^"
				.concat(config.isPwLowercaseRequired() ? PATTERN_LOWER : "")
				.concat(config.isPwUppercaseRequired() ? PATTERN_UPPER : "")
				.concat(config.isPwNumericRequired() ? PATTERN_NUMBER : "")
				.concat(config.isPwSpecialRequired() ? PATTERN_SPECIAL : "")
				.concat("(?=\\S+$).{" + config.getPwMinSize() + "," + config.getPwMaxSize() + "}$")
			);
			//@formatter:on
		}
		log.info("Validating the received password: {} using the current matcher: {}", password, pattern.pattern());
		if (!pattern.matcher(password).matches()) {
			throw new CmsPasswordRequirementsNotMet("The password must have, at least"
				.concat(config.isPwLowercaseRequired() ? ", one lowercase character" : "")
				.concat(config.isPwUppercaseRequired() ? ", one uppercase character" : "")
				.concat(config.isPwNumericRequired() ? ", one numeric character" : "")
				.concat(config.isPwSpecialRequired() ? ", one special character" : "")
				.concat(", and be between " + config.getPwMinSize() + " and " + config.getPwMaxSize() + " characters long.")
			);
		}
	}
	
}
