package com.cs.sigm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("cms")
@Getter
@Setter
public class CmsConfig {
	
	public static final String RESPONSE_SUCCESS = "SUCCESS";
	
	public static final String RESPONSE_USER_NOT_FOUND = "USER_NOT_FOUND";
	
	// TODO: remove if I cannot find a consistent way to use it
	public static final String LOGGING_FILE_PATH = System.getenv("LOGGING_FILE_PATH");
	
	private String version;
	
	private Boolean production;
	
	private int keySize;
	
	private boolean mailAuth;
	
	private boolean mailTLSEnabled;
	
	private String mailUsername;
	
	private String mailPassword;
	
	private String mailHost;
	
	private String mailPort;
	
	private String mailFrom;
	
	private String accountsBaseURL;
	
	private String pwResetFormURL;
	
	private int pwMinSize;
	
	private int pwMaxSize;
	
	private boolean pwLowercaseRequired;
	
	private boolean pwUppercaseRequired;
	
	private boolean pwNumericRequired;
	
	private boolean pwSpecialRequired;
	
	
}
