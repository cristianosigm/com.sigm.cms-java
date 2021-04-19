package com.cs.sigm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
public abstract class CmsTestSetup {
	
	@Autowired
	protected MockMvc mvc;
	
	protected final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	protected void contextLoads() {
		assertThat(mvc != null).isTrue();
	}
	
}
