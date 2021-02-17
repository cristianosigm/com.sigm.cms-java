package com.cs.sigm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class CmsTestSetup {

	@Autowired
	protected MockMvc mvc;

	protected final ObjectMapper mapper = new ObjectMapper();

	// Test Data for Contact Controller
	// -----------------------------------------------------
	// @formatter:off
	/*
	protected ContactDTO contact01 = ContactDTO.builder()
			.name("Test 01")
			.email("test01@test.com")
			.summary("This is the first test case")
			.description("This is the test case 01")
			.build();

	protected ContactDTO contact02 = ContactDTO.builder()
			.name("Test 02")
			.email("test02@test.com")
			.summary("This is the second test case")
			.description("This is the test case 02")
			.build();

	protected ContactDTO contact03 = ContactDTO.builder()
			.name("Test 03")
			.email("test03@test.com")
			.summary("This is the third test case")
			.description("This is the test case 03")
			.build();
			*/
	// @formatter:on

	@Test
	void contextLoads() {
		assertThat(mvc != null).isTrue();
	}

}
