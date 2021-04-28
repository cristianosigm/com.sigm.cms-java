package com.cs.sigm.test.data;

import org.junit.jupiter.api.Disabled;

import com.cs.sigm.adapter.domain.ContentDTO;
import com.cs.sigm.test.CmsTestSetup;

@Disabled
public class ContentServiceTestSetup extends CmsTestSetup {
	
	private final int testSize = 10;
	
	protected ContentDTO[] contentValid = new ContentDTO[testSize];
	
	public ContentServiceTestSetup() {
		for (int pos = 0; pos < testSize; pos++) {
			//@formatter:off
			contentValid[pos] = ContentDTO.builder()
				.content("This is the content " + pos)
				.title("Content " + pos)
				.build();
			//@formatter:on
		}
	}
	
}
