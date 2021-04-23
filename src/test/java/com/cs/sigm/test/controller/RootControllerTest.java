package com.cs.sigm.test.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.cs.sigm.test.CmsTestSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RootControllerTest extends CmsTestSetup {
	
	private static final String BASE_URL = "/";
	
	@Test
	public void shouldGetVersion() throws Exception {
		log.info("|::. TEST .::| >>> Should get the current version");
		final MvcResult result = getVersion().andExpect(status().isOk()).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains("SIGM CMS API - version:"));
	}
	
	private ResultActions getVersion() throws Exception {
		return mvc.perform(get(BASE_URL));
	}
	
}
