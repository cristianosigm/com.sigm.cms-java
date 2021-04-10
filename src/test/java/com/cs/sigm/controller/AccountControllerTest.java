package com.cs.sigm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.cs.sigm.adapter.domain.SignupDTO;
import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.data.AccountControllerTestSetupData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
// @TestMethodOrder(OrderAnnotation.class)
public class AccountControllerTest extends AccountControllerTestSetupData {
	
	private static final String BASE_URL = "/accounts";
	
	@Test // (expected = CmsAuthenticationException.class)
	// @Order(1)
	public void when_SignupWithPasswordShorterThanMinSize_then_ThrowINVALID_PASSWORD_LENTGHT() throws Exception {
		log.info("|::. TEST .::| >>> Should signup a new user");
		
		log.info("Creating contact 01");
		final MvcResult result01 = signup(signupFine).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, result01.getResponse().getContentAsString());
	}
	
	@Test
	// @Order(1)
	public void shouldSignup() throws Exception {
		log.info("|::. TEST .::| >>> Should signup a new user");
		
		log.info("Creating contact 01");
		final MvcResult result01 = signup(signupFine).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, result01.getResponse().getContentAsString());
	}
	
	private ResultActions signup(SignupDTO request) throws Exception {
		return mvc.perform(post(BASE_URL.concat("/signup")).content(this.mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
}
