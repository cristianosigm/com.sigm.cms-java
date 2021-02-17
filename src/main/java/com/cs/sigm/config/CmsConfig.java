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

	private String version;

	private Boolean production;

}
