package com.cs.sigm.data;

import org.junit.jupiter.api.Disabled;

import com.cs.sigm.CmsTestSetup;
import com.cs.sigm.adapter.domain.LoginDTO;
import com.cs.sigm.adapter.domain.SignupDTO;

@Disabled
public class AccountControllerTestSetupData extends CmsTestSetup {
	
	// Test Data for Account Controller
	// -----------------------------------------------------
	// @formatter:off
	
	protected SignupDTO signup01 = SignupDTO.builder()
			.displayName("Signup 01")
			.email("signup01@test.com")
			.name("Valid Signup Request 01")
			.password("Val1dt$t")
			.passwordConfirm("Val1dt$t")
			.build();
	
	protected LoginDTO login01 = LoginDTO.builder()
			.password("Val1dt$t")
			.username("signup01@test.com")
			.build();

	protected SignupDTO signup02 = SignupDTO.builder()
			.displayName("Signup 02")
			.email("signup02@test.com")
			.name("Valid Signup Request 02")
			.password("Val1dt$t")
			.passwordConfirm("Val1dt$t")
			.build();

	protected LoginDTO login02 = LoginDTO.builder()
		.password("Val1dt$t")
		.username("signup02@test.com")
		.build();

	protected SignupDTO signup03 = SignupDTO.builder()
		.displayName("Signup 03")
		.email("signup03@test.com")
		.name("Valid Signup Request 03")
		.password("Val1dt$t")
		.passwordConfirm("Val1dt$t")
		.build();

	protected LoginDTO login03 = LoginDTO.builder()
		.password("Val1dt$t")
		.username("signup03@test.com")
		.build();

	protected SignupDTO signupNotValidated = SignupDTO.builder()
		.displayName("Signup Not Validate")
		.email("signupnv@test.com")
		.name("Valid Signup Request - Do not validate")
		.password("Val1dt$t")
		.passwordConfirm("Val1dt$t")
		.build();
	
	protected LoginDTO loginNotValidated = LoginDTO.builder()
		.password("Val1dt$t")
		.username("signupnv@test.com")
		.build();
	
	protected LoginDTO loginInvalid = LoginDTO.builder()
		.password("Val1dt$t")
		.username("signupinvalid@test.com")
		.build();
	
	// @formatter:on
}
