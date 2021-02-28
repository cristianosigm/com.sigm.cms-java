package com.cs.sigm.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.cs.sigm.CmsTestSetup;
import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.config.CmsConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest extends CmsTestSetup {
	
	// TODO: adjust test initialization before uncomment this.
	
	/*
	private UserDTO user01 = UserDTO.builder()
			.displayName("Cris 01")
			.email("cristianojava@gmail.com")
			.name("Cristiano Souza 01")
			.password("123pin")
			.username("cristianojava@gmail.com")
			.build();

	private UserDTO user02 = UserDTO.builder()
			.displayName("Cris 02")
			.email("cristianojava2@gmail.com")
			.name("Cristiano Souza 02")
			.password("456pin")
			.username("cristianojava2@gmail.com")
			.build();
	
	private UserDTO user03 = UserDTO.builder()
			.displayName("Cris 03")
			.email("cristianojava3@gmail.com")
			.name("Cristiano Souza 03")
			.password("789pin")
			.username("cristianojava3@gmail.com")
			.build();
	
	private UserDTO user03sameEmail = UserDTO.builder()
			.displayName("Cris 04")
			.email("cristianojava3@gmail.com")
			.name("Cristiano Souza 04")
			.password("012pin")
			.username("cristianojava3@gmail.com")
			.build();
	

	private static final String BASE_URL = "/user";

	@Test
	@Order(1)
	public void shouldSave() throws Exception {
		log.info("|::. TEST .::| >>> Should create 3 Contacts");

		log.info("Creating contact 01");
		final MvcResult result01 = createSingle(user01).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, result01.getResponse().getContentAsString());
		log.info("Creating contact 02");
		final MvcResult result02 = createSingle(user02).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, result02.getResponse().getContentAsString());
		log.info("Creating contact 03");
		final MvcResult result03 = createSingle(user03).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, result03.getResponse().getContentAsString());
	}

	@Test
	@Order(2)
	public void shouldFindAll() throws Exception {
		log.info("|::. TEST .::| >>> Should find 3 Contacts");
		findAll().andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].name", is("Cris 01")));
	}

	@Test
	@Order(3)
	public void shouldFindSingleById() throws Exception {
		log.info("|::. TEST .::| >>> Should find a Contact by its Id");
		findSingle(1L).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Cris 01")));
	}

	@Test
	@Order(4)
	public void shouldDeleteById() throws Exception {
		log.info("|::. TEST .::| >>> Should delete a Contact by its Id");
		final MvcResult resultDelete = deleteSingle(1L).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, resultDelete.getResponse().getContentAsString());
		log.info("Checking if there is one less contact.");
		findAll().andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	@Order(5)
	public void shouldFailToSave() throws Exception {
		log.info("|::. TEST .::| >>> Should fail to save 2 users with the same email");

		log.info("Creating contact 04 with same email of 03");
		final MvcResult result01 = createSingle(user03sameEmail).andReturn();
		assertEquals(CmsConfig.RESPONSE_SUCCESS, result01.getResponse().getContentAsString());
	}


	private ResultActions createSingle(UserDTO request) throws Exception {
		return mvc.perform(post(BASE_URL).content(this.mapper.writeValueAsBytes(request))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions deleteSingle(Long id) throws Exception {
		return mvc.perform(post(BASE_URL + "/delete/" + id).accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions findSingle(Long id) throws Exception {
		return mvc.perform(get(BASE_URL + "/single/" + id).accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions findAll() throws Exception {
		return mvc.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON));
	}
	*/
}
