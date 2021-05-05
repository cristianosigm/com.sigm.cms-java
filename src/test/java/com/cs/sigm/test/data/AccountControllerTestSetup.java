package com.cs.sigm.test.data;

import org.junit.jupiter.api.Disabled;

import com.cs.sigm.adapter.domain.LoginDTO;
import com.cs.sigm.adapter.domain.SignupDTO;
import com.cs.sigm.test.CmsTestSetup;

@Disabled
public class AccountControllerTestSetup extends CmsTestSetup {

	private final int testSize = 10;

	protected SignupDTO[] signupValid = new SignupDTO[testSize];

	//@formatter:off
	public AccountControllerTestSetup() {
		for (int pos = 0; pos < testSize; pos++) {
			signupValid[pos] = SignupDTO.builder()
				.displayName("Signup " + pos)
				.email("signup" + pos + "@test.com")
				.name("Valid Signup Request " + pos)
				.password("Val1dt$t")
				.passwordConfirm("Val1dt$t")
				.build();
		}
	}

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
