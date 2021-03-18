package com.cs.sigm.data;

import org.junit.jupiter.api.Disabled;

import com.cs.sigm.CmsTestSetup;
import com.cs.sigm.adapter.domain.SignupDTO;

@Disabled
public class AccountControllerTestSetupData extends CmsTestSetup {

	// Test Data for Account Controller
	// -----------------------------------------------------
	// @formatter:off
	protected SignupDTO signupFine = SignupDTO.builder()
			.displayName("Signup 01")
			.email("signup01@test.com")
			.name("Signup Zero One")
			.password("test123")
			.passwordConfirm("test123")
			.build();
	// @formatter:on

}
