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
import com.cs.sigm.adapter.domain.PasswordResetDTO;
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
		performValidSignup(signupValid[0]);
	}
	
	@Test
	public void when_LoginWithAccountNotValidated_then_Fail() throws Exception {
		performValidSignup(signupNotValidated);
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
		final SignupDTO curSignup = signupValid[1];
		performValidSignup(curSignup);
		final User usr = loadUserByEmail(curSignup.getEmail());
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
		final SignupDTO curSignup = signupValid[2];
		performValidSignup(curSignup);
		performAccountValidation(curSignup);
	}
	
	@Test
	public void when_LoginWithValidatedAccount_then_Success() throws Exception {
		final SignupDTO curSignup = signupValid[3];
		final LoginDTO curLogin = loginValid[3];
		performValidSignup(curSignup);
		final User usr = performAccountValidation(curSignup);
		//@formatter:off
		login(curLogin)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(usr.getId().toString()))
			.andExpect(jsonPath("$.name").value(usr.getName()))
			.andExpect(jsonPath("$.validated").value(Boolean.TRUE.toString()))
			;
		//@formatter:on
	}
	
	@Test
	public void when_MaxfailedLoginAttemptsWithInvalidCredentials_then_AccountIsLocked() throws Exception {
		performSignupThenlockAnAccountAndConfirm(signupValid[4], loginValid[4]);
	}
	
	@Test
	public void when_ValidPasswordResetRequestedForLockedAccount_then_AccountUnlocked() throws Exception {
		final SignupDTO curSignup = signupValid[5];
		final LoginDTO curLogin = loginValid[5];
		performSignupThenlockAnAccountAndConfirm(curSignup, curLogin);
		resetPassword(curSignup);
		final User usr = loadUserByEmail(curSignup.getEmail());
		curLogin.setPassword("V4l1dP4$$w4rd");
		//@formatter:off
		login(curLogin)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(usr.getId().toString()))
			.andExpect(jsonPath("$.name").value(usr.getName()))
			.andExpect(jsonPath("$.blocked").value(Boolean.FALSE.toString()))
			;
		//@formatter:on
	}
	
	@Test
	public void when_InvalidPasswordResetRequested_then_Fail() throws Exception {
		final SignupDTO curSignup = signupValid[6];
		signup(curSignup).andExpect(status().isOk());
		resetRequest(curSignup.getEmail());
		//@formatter:off
		final PasswordResetDTO pwReset = PasswordResetDTO.builder()
			.email(curSignup.getEmail())
			.password("V4l1dP4$$w4rd")
			.passwordConfirm("V4l1dP4$$w4rd")
			.resetKey("InvalidPasswordResetKey")
			.build();
		resetProcess(pwReset)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_INVALID_CREDENTIALS.getCode())))
			.andExpect(jsonPath("$.details").value(containsString("Invalid password reset request")))
			;
		//@formatter:on
	}
	
	@Test
	public void when_ValidPasswordResetRequested_then_ResetPassword() throws Exception {
		final SignupDTO curSignup = signupValid[7];
		final LoginDTO curLogin = loginValid[7];
		performSignupThenlockAnAccountAndConfirm(curSignup, curLogin);
		resetPassword(curSignup);
	}
	
	private User loadUserByEmail(String email) throws Exception {
		return this.userRepository.findByEmail(email).orElseThrow(() -> new CmsEntryNotFoundException("Failed to find the test user via email."));
	}
	
	private void performValidSignup(final SignupDTO targetSignup) throws Exception {
		signup(targetSignup).andExpect(status().isOk());
	}
	
	private User performAccountValidation(final SignupDTO targetSignup) throws Exception {
		final User usr = loadUserByEmail(targetSignup.getEmail());
		validate(usr.getId(), usr.getValidationKey()).andExpect(status().isOk());
		return usr;
	}
	
	private void resetPassword(SignupDTO targetSignup) throws Exception {
		resetRequest(targetSignup.getEmail());
		final User usr = loadUserByEmail(targetSignup.getEmail());
		final PasswordResetDTO pwReset = PasswordResetDTO.builder().email(usr.getEmail()).password("V4l1dP4$$w4rd").passwordConfirm("V4l1dP4$$w4rd").resetKey(usr.getPasswordResetKey()).build();
		resetProcess(pwReset).andExpect(status().isOk());
	}
	
	private void performSignupThenlockAnAccountAndConfirm(final SignupDTO targetSignup, final LoginDTO targetLogin) throws Exception {
		performValidSignup(targetSignup);
		final User usr = loadUserByEmail(targetSignup.getEmail());
		validate(usr.getId(), usr.getValidationKey()).andExpect(status().isOk());
		targetLogin.setPassword("Wr0ngp4$$0rd");
		for (int i = 0; i <= (config.getPwMaxIncorrectTries()); i++) {
			//@formatter:off
			login(targetLogin)
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_INVALID_CREDENTIALS.getCode())))
			.andExpect(jsonPath("$.details").isEmpty())
			;
			//@formatter:on
		}
		//@formatter:off
		login(targetLogin)
		.andExpect(status().is5xxServerError())
		.andExpect(jsonPath("$.error").value(containsString(ErrorCode.SEC_ACCOUNT_LOCKED.getCode())))
		.andExpect(jsonPath("$.details").value(containsString("Please reset your account to proceed")))
		;
		//@formatter:on
		final User usrLocked = loadUserByEmail(targetSignup.getEmail());
		log.info(" Current status of the user: failedAttempts = " + usrLocked.getFailedAttempts().toString() + "; blocked = " + usrLocked.getBlocked().toString());
		assertTrue(usrLocked.getBlocked().booleanValue());
	}
	
	private ResultActions signup(SignupDTO request) throws Exception {
		return mvc.perform(post(BASE_URL.concat("/signup")).content(this.mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions login(LoginDTO request) throws Exception {
		return mvc.perform(post(BASE_URL.concat("/login")).content(this.mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions validate(Long id, String email) throws Exception {
		return mvc.perform(get(BASE_URL.concat("/validate/").concat(id.toString()).concat("/").concat(email)).accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions resetRequest(String email) throws Exception {
		return mvc.perform(get(BASE_URL.concat("/reset/").concat(email)).accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions resetProcess(PasswordResetDTO request) throws Exception {
		return mvc.perform(post(BASE_URL.concat("/reset")).content(this.mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}
	
}
