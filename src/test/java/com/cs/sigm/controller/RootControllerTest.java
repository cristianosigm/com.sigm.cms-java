package com.cs.sigm.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.cs.sigm.CmsTestSetup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RootControllerTest extends CmsTestSetup {

	private static final String BASE_URL = "/";

	@Test
	@Order(1)
	public void shouldGetVersion() throws Exception {
//		log.info("|::. TEST .::| >>> Should get the current version");
		final MvcResult result = getVersion().andExpect(status().isOk()).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains("Marketing Portal API - version:"));
	}

	private ResultActions getVersion() throws Exception {
		return mvc.perform(get(BASE_URL));
	}

}
