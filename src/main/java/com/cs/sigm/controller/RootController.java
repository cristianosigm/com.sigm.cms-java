package com.cs.sigm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.config.CmsConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class RootController {

	@Autowired
	private CmsConfig config;

	@GetMapping
	public String getVersion() {
		
		log.info("Log path: " + CmsConfig.LOGGING_FILE_PATH);
		
		return "SIGM CMS API - version: ".concat(config.getVersion());
	}

}
