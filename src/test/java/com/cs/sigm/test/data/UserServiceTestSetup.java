package com.cs.sigm.test.data;

import org.junit.jupiter.api.Disabled;

import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.test.CmsTestSetup;

@Disabled
public class UserServiceTestSetup extends CmsTestSetup {
	
	private final int testSize = 10;
	
	protected UserDTO[] userValid = new UserDTO[testSize];
	
	public UserServiceTestSetup() {
		for (int pos = 0; pos < testSize; pos++) {
			//@formatter:off
			userValid[pos] = UserDTO.builder()
				.displayName("User " + pos)
				.email("user" + pos + "@test.com")
				.name("Valid User " + pos)
				.password("Val1dt$t")
				.passwordConfirm("Val1dt$t")
				.build();
			//@formatter:on
		}
	}
	
}
