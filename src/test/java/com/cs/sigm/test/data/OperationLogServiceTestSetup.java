package com.cs.sigm.test.data;

import org.junit.jupiter.api.Disabled;

import com.cs.sigm.adapter.domain.ContentDTO;
import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.test.CmsTestSetup;

@Disabled
public class OperationLogServiceTestSetup extends CmsTestSetup {

	private final int testSize = 5;

	protected UserDTO[] users = new UserDTO[testSize];

	protected ContentDTO[] contents = new ContentDTO[testSize];

	public OperationLogServiceTestSetup() {
		for (int pos = 0; pos < testSize; pos++) {
			//@formatter:off
			users[pos] = UserDTO.builder()
					.displayName("User " + pos)
					.email("oluser" + pos + "@test.com")
					.name("Valid User " + pos)
					.password("Val1dt$t")
					.passwordConfirm("Val1dt$t")
					.build();
			contents[pos] = ContentDTO.builder()
					.content("This is the content " + pos)
					.title("Content " + pos)
					.build();
			//@formatter:on
		}
	}

}
