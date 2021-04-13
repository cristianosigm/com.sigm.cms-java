package com.cs.sigm.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.subethamail.wiser.Wiser;

import com.cs.sigm.adapter.domain.LoginDTO;
import com.cs.sigm.adapter.domain.SignupDTO;
import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.data.AccountControllerTestSetupData;
import com.cs.sigm.domain.User;
import com.cs.sigm.exception.CmsEntryNotFoundException;
import com.cs.sigm.exception.ErrorCode;
import com.cs.sigm.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class AccountControllerTest extends AccountControllerTestSetupData {
	
	private static final String BASE_URL = "/accounts";
	
	private static Wiser wiser;
	
	@Autowired
	private CmsConfig config;
	
	@Autowired
	private UserRepository userRepository;
	
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
		final SignupDTO signupFail = signupValid[0];
		signupFail.setPassword("F41l3d#");
		signupFail.setPasswordConfirm("F41l3d#");
		//@formatter:off
		signup(signupFail)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_PASSWORD_REQUIREMENTS_NOT_MET.getCode())))
			.andExpect(jsonPath("$.details").value(containsString("and be between")))
			;
		//@formatter:on
	}
	
	@Test
	public void when_SignupWithPasswordWithoutUppercase_then_Fail() throws Exception {
		final SignupDTO signupFail = signupValid[0];
		signupFail.setPassword("f41l3dup#r");
		signupFail.setPasswordConfirm("f41l3dup#r");
		//@formatter:off
		signup(signupFail)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_PASSWORD_REQUIREMENTS_NOT_MET.getCode())))
			.andExpect(jsonPath("$.details").value(containsString("one uppercase character")))
			;
		//@formatter:on
	}
	
	@Test
	public void when_SignupWithPasswordWithoutLowercase_then_Fail() throws Exception {
		final SignupDTO signupFail = signupValid[0];
		signupFail.setPassword("F41L3DL0W#R");
		signupFail.setPasswordConfirm("F41L3DL0W#R");
		//@formatter:off
		signup(signupFail)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_PASSWORD_REQUIREMENTS_NOT_MET.getCode())))
			.andExpect(jsonPath("$.details").value(containsString("one lowercase character")))
			;
		//@formatter:on
	}
	
	@Test
	public void when_SignupWithPasswordWithoutSpecial_then_Fail() throws Exception {
		final SignupDTO signupFail = signupValid[0];
		signupFail.setPassword("F4iledSp3c14l");
		signupFail.setPasswordConfirm("F4iledSp3c14l");
		//@formatter:off
		signup(signupFail)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_PASSWORD_REQUIREMENTS_NOT_MET.getCode())))
			.andExpect(jsonPath("$.details").value(containsString("one special character")))
			;
		//@formatter:on
	}
	
	@Test
	public void when_SignupWithNoPasswordConfirmationMath_then_Fail() throws Exception {
		final SignupDTO signupFail = signupValid[0];
		signupFail.setPassword("P4ssw0rdN0tM4tc#");
		signupFail.setPasswordConfirm("P4ssw0rdN0tM4tch");
		//@formatter:off
		signup(signupFail)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_INVALID_CREDENTIALS.getCode())))
			.andExpect(jsonPath("$.details").value(containsString("The confirmation password do not match")))
			;
		//@formatter:on
	}
	
	@Test
	public void when_SignupWithBlankPassword_then_Fail() throws Exception {
		final SignupDTO signupFail = signupValid[0];
		signupFail.setPassword("         ");
		signupFail.setPasswordConfirm("         ");
		//@formatter:off
		signup(signupFail)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_INVALID_CREDENTIALS.getCode())))
			.andExpect(jsonPath("$.details").value(containsString("The new password must not be blank")))
			;
		//@formatter:on
	}
	
	@Test
	public void when_SignupValid_then_Success() throws Exception {
		signup(signupValid[0]).andExpect(status().isOk());
	}
	
	@Test
	public void when_LoginWithAccountNotValidated_then_Fail() throws Exception {
		signup(signupNotValidated).andExpect(status().isOk());
		//@formatter:off
		login(loginNotValidated)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_MISSING_VALIDATION.getCode())))
			.andExpect(jsonPath("$.details").value(containsString("Account not validated")))
			;
		//@formatter:on
	}
	
	@Test
	public void when_LoginWithNonExistingUser_then_Fail() throws Exception {
		//@formatter:off
		login(loginNotValidated)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_INVALID_CREDENTIALS.getCode())))
			.andExpect(jsonPath("$.details").isEmpty())
			;
		//@formatter:on		
	}
	
	@Test
	public void when_ValidateAccountWithInvalidKey_then_Fail() throws Exception {
		// signup
		signup(signupValid[1]).andExpect(status().isOk());
		// validate the account
		final User usr = this.userRepository.findByEmail(signupValid[1].getEmail()).orElseThrow(() -> new CmsEntryNotFoundException("Failed to find the test user via email."));
		//@formatter:off
		validate(usr.getId(), usr.getValidationKey().concat("x"))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_INVALID_CREDENTIALS.getCode())))
			.andExpect(jsonPath("$.details").value(containsString("Invalid validation key received")))
			;
		//@formatter:on		
	}
	
	@Test
	public void when_ValidateAccountWithValidKey_then_Success() throws Exception {
		// signup
		signup(signupValid[2]).andExpect(status().isOk());
		// validate the account
		final User usr = this.userRepository.findByEmail(signupValid[2].getEmail()).orElseThrow(() -> new CmsEntryNotFoundException("Failed to find the test user via email."));
		validate(usr.getId(), usr.getValidationKey()).andExpect(status().isOk());
	}
	
	@Test
	public void when_LoginWithValidatedAccount_then_Success() throws Exception {
		// signup
		signup(signupValid[3]).andExpect(status().isOk());
		// validate the account
		final User usr = this.userRepository.findByEmail(signupValid[3].getEmail()).orElseThrow(() -> new CmsEntryNotFoundException("Failed to find the test user via email."));
		validate(usr.getId(), usr.getValidationKey()).andExpect(status().isOk());
		// login
		//@formatter:off
		login(loginValid[3])
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(usr.getId().toString()))
			.andExpect(jsonPath("$.name").value(usr.getName()))
			.andExpect(jsonPath("$.validated").value(Boolean.TRUE.toString()))
			;
		//@formatter:on
	}
	
	@Test
	public void when_MaxfailedLoginAttemptsWithInvalidCredentials_then_AccountIsLocked() throws Exception {
		// signup
		signup(signupValid[4]).andExpect(status().isOk());
		// validate the account
		final User usr = this.userRepository.findByEmail(signupValid[4].getEmail()).orElseThrow(() -> new CmsEntryNotFoundException("Failed to find the test user via email."));
		validate(usr.getId(), usr.getValidationKey()).andExpect(status().isOk());
		// locking up account
		loginValid[4].setPassword("Wr0ngp4$$0rd");
		for (int i = 0; i <= (config.getPwMaxIncorrectTries()); i++) {
			//@formatter:off
			login(loginValid[4])
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_INVALID_CREDENTIALS.getCode())))
			.andExpect(jsonPath("$.details").isEmpty())
			;
			//@formatter:on
		}
		// next attempt will get a different exception
		
		//@formatter:off
		login(loginValid[4])
		.andExpect(status().is5xxServerError())
		.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_ACCOUNT_LOCKED.getCode())))
		.andExpect(jsonPath("$.details").value(containsString("Please reset your account to proceed")))
		;
		//@formatter:on
		
		// checking if is locked
		final User usrLocked = this.userRepository.findByEmail(signupValid[4].getEmail()).orElseThrow(() -> new CmsEntryNotFoundException("Failed to find the test user via email."));
		log.info(" Current status of the user: failedAttempts = " + usrLocked.getFailedAttempts().toString() + "; blocked = " + usrLocked.getBlocked().toString());
		assertTrue(usrLocked.getBlocked().booleanValue());
	}
	
	@Test
	public void when_UnlockAccount_then_AccountUnlocked() throws Exception {
	}
	
	private ResultActions signup(SignupDTO request) throws Exception {
		return mvc.perform(post(BASE_URL.concat("/signup")).content(this.mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions login(LoginDTO request) throws Exception {
		return mvc.perform(post(BASE_URL.concat("/login")).content(this.mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions validate(Long id, String email) throws Exception {
		return mvc.perform(get(BASE_URL.concat("/validate/".concat(id.toString()).concat("/").concat(email))).accept(MediaType.APPLICATION_JSON));
	}
	
}
