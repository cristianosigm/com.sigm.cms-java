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

import lombok.extern.slf4j.Slf4j;
import rb.la.sa.marketing.MarketingTestSetup;
import rb.la.sa.marketing.adapter.domain.ContactDTO;
import rb.la.sa.marketing.config.MarketingConfig;

@Slf4j
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ContactControllerTest extends MarketingTestSetup {

	private static final String BASE_URL = "/contact";

	@Test
	@Order(1)
	public void shouldSave() throws Exception {
		log.info("|::. TEST .::| >>> Should create a Contact");

		log.info("Creating contact 01");
		final MvcResult result01 = createSingle(contact01).andReturn();
		assertEquals(MarketingConfig.RESPONSE_SUCCESS, result01.getResponse().getContentAsString());
		log.info("Creating contact 02");
		final MvcResult result02 = createSingle(contact02).andReturn();
		assertEquals(MarketingConfig.RESPONSE_SUCCESS, result02.getResponse().getContentAsString());
		log.info("Creating contact 03");
		final MvcResult result03 = createSingle(contact03).andReturn();
		assertEquals(MarketingConfig.RESPONSE_SUCCESS, result03.getResponse().getContentAsString());
	}

	@Test
	@Order(2)
	public void shouldFindAll() throws Exception {
		log.info("|::. TEST .::| >>> Should find 3 Contacts");
		findAll().andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[0].name", is("Test 01")));
	}

	@Test
	@Order(3)
	public void shouldFindSingleById() throws Exception {
		log.info("|::. TEST .::| >>> Should find a Contact by its Id");
		findSingle(1L).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Test 01")));
	}

	@Test
	@Order(4)
	public void shouldDeleteById() throws Exception {
		log.info("|::. TEST .::| >>> Should delete a Contact by its Id");
		final MvcResult resultDelete = deleteSingle(1L).andReturn();
		assertEquals(MarketingConfig.RESPONSE_SUCCESS, resultDelete.getResponse().getContentAsString());
		log.info("Checking if there is one less contact.");
		findAll().andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}

	private ResultActions createSingle(ContactDTO request) throws Exception {
		return mvc.perform(post(BASE_URL).content(this.mapper.writeValueAsBytes(request)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
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

}
