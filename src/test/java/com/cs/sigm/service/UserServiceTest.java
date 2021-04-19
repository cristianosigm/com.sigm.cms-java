package com.cs.sigm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.subethamail.wiser.Wiser;

import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.data.UserServiceTestSetup;
import com.cs.sigm.domain.User;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.exception.CmsAuthenticationException;
import com.cs.sigm.exception.CmsPasswordRequirementsNotMet;
import com.cs.sigm.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class UserServiceTest extends UserServiceTestSetup {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserMapper mapper;
	
	private static Wiser wiser;
	
	@BeforeAll
	public static void setUp() throws Exception {
		wiser = new Wiser(2525);
		wiser.start();
	}
	
	@AfterAll
	public static void tearDown() throws Exception {
		wiser.stop();
	}
	
	@Test
	public void when_CreateUserWithPasswordShorterThanMinSize_then_Fail() throws Exception {
		final UserDTO request = userValid[0];
		request.setPassword("F41l3d#");
		request.setPasswordConfirm("F41l3d#");
		final Exception exception = assertThrows(CmsPasswordRequirementsNotMet.class, () -> {
			service.save(mapper.map(request), Operation.CREATE, 1L);
		});
		log.info(" MESSAGE CAUGHT: " + exception.getMessage());
		assertTrue(exception.getMessage().contains("and be between"));
	}
	
	@Test
	public void when_CreateUserWithPasswordWithoutUppercase_then_Fail() throws Exception {
		final UserDTO request = userValid[0];
		request.setPassword("f41l3dup#r");
		request.setPasswordConfirm("f41l3dup#r");
		final Exception exception = assertThrows(CmsPasswordRequirementsNotMet.class, () -> {
			service.save(mapper.map(request), Operation.CREATE, 1L);
		});
		log.info(" MESSAGE CAUGHT: " + exception.getMessage());
		assertTrue(exception.getMessage().contains(", one uppercase character"));
	}
	
	@Test
	public void when_CreateUserWithPasswordWithoutLowercase_then_Fail() throws Exception {
		final UserDTO request = userValid[0];
		request.setPassword("F41L3DL0W#R");
		request.setPasswordConfirm("F41L3DL0W#R");
		final Exception exception = assertThrows(CmsPasswordRequirementsNotMet.class, () -> {
			service.save(mapper.map(request), Operation.CREATE, 1L);
		});
		log.info(" MESSAGE CAUGHT: " + exception.getMessage());
		assertTrue(exception.getMessage().contains(", one lowercase character"));
	}
	
	@Test
	public void when_CreateUserWithPasswordWithoutSpecial_then_Fail() throws Exception {
		final UserDTO request = userValid[0];
		request.setPassword("F4iledSp3c14l");
		request.setPasswordConfirm("F4iledSp3c14l");
		final Exception exception = assertThrows(CmsPasswordRequirementsNotMet.class, () -> {
			service.save(mapper.map(request), Operation.CREATE, 1L);
		});
		log.info(" MESSAGE CAUGHT: " + exception.getMessage());
		assertTrue(exception.getMessage().contains(", one special character"));
	}
	
	@Test
	public void when_CreateUserWithNoPasswordConfirmationMath_then_Fail() throws Exception {
		final UserDTO request = userValid[0];
		request.setPassword("P4ssw0rdN0tM4tc#");
		request.setPasswordConfirm("P4ssw0rdN0tM4tch");
		final Exception exception = assertThrows(CmsAuthenticationException.class, () -> {
			service.save(mapper.map(request), Operation.CREATE, 1L);
		});
		log.info(" MESSAGE CAUGHT: " + exception.getMessage());
		assertTrue(exception.getMessage().contains("The confirmation password do not match"));
	}
	
	@Test
	public void when_CreateUserWithBlankPassword_then_Fail() throws Exception {
		final UserDTO request = userValid[0];
		request.setPassword("         ");
		request.setPasswordConfirm("         ");
		final Exception exception = assertThrows(CmsAuthenticationException.class, () -> {
			service.save(mapper.map(request), Operation.CREATE, 1L);
		});
		log.info(" MESSAGE CAUGHT: " + exception.getMessage());
		assertTrue(exception.getMessage().contains("The new password must not be blank"));
	}
	
	@Test
	public void when_userValid_then_Success() throws Exception {
		final UserDTO request = userValid[0];
		final User result = service.save(mapper.map(request), Operation.CREATE, 1L);
		assertEquals(request.getEmail(), result.getUsername());
		assertTrue(!result.getApproved().booleanValue());
	}
	
	@Test
	public void when_findAllWithNoData_then_Success() throws Exception {
		
	}
	
	@Test
	public void when_findAllWithData_then_Success() throws Exception {
		
	}
	
	@Test
	public void when_findSingleWithInvalidId_then_Fail() throws Exception {
		
	}
	
	@Test
	public void when_findSingleWithValidId_then_Success() throws Exception {
		
	}
	
	@Test
	public void when_updateWithValidPasswordChange_then_Success() throws Exception {
		
	}
	
	@Test
	public void when_updateWithInvalidPasswordChange_then_Fail() throws Exception {
		
	}
	
	@Test
	public void when_deleteWithInvalidId_then_Fail() throws Exception {
		
	}
	
	@Test
	public void when_deleteWithValidId_then_Success() throws Exception {
		
	}
	
}
