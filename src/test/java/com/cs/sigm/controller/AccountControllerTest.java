package com.cs.sigm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.subethamail.wiser.Wiser;

import com.cs.sigm.adapter.domain.LoginDTO;
import com.cs.sigm.adapter.domain.SignupDTO;
import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.data.AccountControllerTestSetupData;
import com.cs.sigm.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class AccountControllerTest extends AccountControllerTestSetupData {
	
	private static final String BASE_URL = "/accounts";
	
	private static Wiser wiser;
	
	@BeforeAll
	public static void setUp() throws Exception {
		wiser = new Wiser(2525);
		wiser.start();
	}
	
	@AfterAll
	public static void tearDown() throws Exception {
		wiser.stop();
	}
	
	@Test
	public void when_SignupWithPasswordShorterThanMinSize_then_Fail() throws Exception {
		final SignupDTO signupFail = signup01;
		signupFail.setPassword("F41l3d#");
		signupFail.setPasswordConfirm("F41l3d#");
		final MvcResult result = signup(signupFail).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains(ErrorCode.SEC_PASSWORD_REQUIREMENTS_NOT_MET.getCode()));
	}
	
	@Test
	public void when_SignupWithPasswordWithoutUppercase_then_Fail() throws Exception {
		final SignupDTO signupFail = signup01;
		signupFail.setPassword("f41l3dup#r");
		signupFail.setPasswordConfirm("f41l3dup#r");
		final MvcResult result = signup(signupFail).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains(ErrorCode.SEC_PASSWORD_REQUIREMENTS_NOT_MET.getCode()));
	}
	
	@Test
	public void when_SignupWithPasswordWithoutLowercase_then_Fail() throws Exception {
		final SignupDTO signupFail = signup01;
		signupFail.setPassword("F41L3DL0W#R");
		signupFail.setPasswordConfirm("F41L3DL0W#R");
		final MvcResult result = signup(signupFail).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains(ErrorCode.SEC_PASSWORD_REQUIREMENTS_NOT_MET.getCode()));
	}
	
	@Test
	public void when_SignupWithPasswordWithoutSpecial_then_Fail() throws Exception {
		final SignupDTO signupFail = signup01;
		signupFail.setPassword("F4iledSp3c14l");
		signupFail.setPasswordConfirm("F4iledSp3c14l");
		final MvcResult result = signup(signupFail).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains(ErrorCode.SEC_PASSWORD_REQUIREMENTS_NOT_MET.getCode()));
	}
	
	@Test
	public void when_SignupWithNoPasswordConfirmationMath_then_Fail() throws Exception {
		final SignupDTO signupFail = signup01;
		signupFail.setPassword("P4ssw0rdN0tM4tc#");
		signupFail.setPasswordConfirm("P4ssw0rdN0tM4tch");
		final MvcResult result = signup(signupFail).andReturn();
		log.info(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains(ErrorCode.SEC_INVALID_CREDENTIALS.getCode()));
	}
	
	@Test
	public void when_SignupWithBlankPassword_then_Fail() throws Exception {
		final SignupDTO signupFail = signup01;
		signupFail.setPassword("         ");
		signupFail.setPasswordConfirm("         ");
		final MvcResult result = signup(signupFail).andReturn();
		log.info(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains(ErrorCode.SEC_INVALID_CREDENTIALS.getCode()));
	}
	
	@Test
	public void when_SignupValid_then_Success() throws Exception {
		final MvcResult result = signup(signup01).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, result.getResponse().getContentAsString());
	}
	
	@Test
	public void when_LoginWithAccountNotValidated_then_Fail() throws Exception {
		final MvcResult signupResult = signup(signupNotValidated).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, signupResult.getResponse().getContentAsString());
		final MvcResult result = login(loginNotValidated).andReturn();
		log.info(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains(ErrorCode.SEC_MISSING_VALIDATION.getCode()));
	}
	
	@Test
	public void when_LoginWithNonExistingUser_then_Fail() throws Exception {
		final MvcResult result = login(loginInvalid).andReturn();
		log.info(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains(ErrorCode.SEC_INVALID_CREDENTIALS.getCode()));
	}
	
	@Test
	public void when_LoginWithValidatedAccount_then_Success() throws Exception {
		final MvcResult signupResult = signup(signup02).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, signupResult.getResponse().getContentAsString());
		
		// TODO: validate the account
		
		final MvcResult result = login(login02).andReturn();
		log.info(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains(ErrorCode.SEC_MISSING_VALIDATION.getCode()));
	}
	
	// TODO: ---------------------------------------------------------------------
	/*
	 * @Test public void when_MaxfailedLoginAttemptsWithInvalidCredentials_then_AccountIsLocked() throws Exception { assertTrue(false); }
	 * @Test public void when_UnlockAccount_then_AccountUnlocked() throws Exception { assertTrue(false); }
	 * @Test public void when_ValidateAccountWithInvalidKey_then_Fail() throws Exception { assertTrue(false); }
	 * @Test public void when_ValidateAccountWithValidKey_then_Success() throws Exception { assertTrue(false); } //
	 */
	
	private ResultActions signup(SignupDTO request) throws Exception {
		return mvc.perform(post(BASE_URL.concat("/signup")).content(this.mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions login(LoginDTO request) throws Exception {
		return mvc.perform(post(BASE_URL.concat("/login")).content(this.mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
}
