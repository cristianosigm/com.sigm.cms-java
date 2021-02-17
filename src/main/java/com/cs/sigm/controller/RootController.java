package com.cs.sigm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.config.CmsConfig;

@RestController
@RequestMapping(value = "/")
public class RootController {

	@Autowired
	private CmsConfig config;

	@GetMapping
	public String getVersion() {
		return "Marketing Portal API - version: ".concat(config.getVersion());
	}

}
