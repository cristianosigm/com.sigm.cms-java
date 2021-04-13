package com.cs.sigm.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.adapter.domain.LoginDTO;
import com.cs.sigm.adapter.domain.PasswordResetDTO;
import com.cs.sigm.adapter.domain.SignupDTO;
import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.domain.User;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.domain.fixed.Role;
import com.cs.sigm.mapper.UserMapper;
import com.cs.sigm.service.LoginService;
import com.cs.sigm.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserMapper mapper;
	
	@ApiOperation(code = 200, value = "Validate the provided Credentials and, if valid, creates a new back-end session.", response = UserDTO.class)
	@PostMapping("/login")
	public UserDTO login(final HttpServletResponse servletResponse, @Valid @RequestBody final LoginDTO login) {
		final User loggedUser = loginService.checkLogin(login);
		servletResponse.addCookie(new Cookie("userId", loggedUser.getId().toString()));
		servletResponse.addCookie(new Cookie("roleId", loggedUser.getIdRole().toString()));
		servletResponse.addCookie(new Cookie("roleName", Role.getKeyById(loggedUser.getIdRole())));
		return mapper.map(loggedUser);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody SignupDTO request) {
		// TODO: set the admin user which performed the action.
		service.save(mapper.map(parse(request)), Operation.SIGNUP, 1L);
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}
	
	@GetMapping("/reset/{email}")
	public ResponseEntity<String> resetRequest(@PathVariable String email) {
		if (service.requestPasswordReset(email)) {
			// worked fine
			return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
		}
		// something gone wrong....
		return new ResponseEntity<>(CmsConfig.RESPONSE_USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/reset")
	public ResponseEntity<String> resetProcess(@Valid @RequestBody PasswordResetDTO request) {
		service.processPasswordReset(request.getEmail(), request.getResetKey(), request.getPassword(), request.getPasswordConfirm());
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}
	
	@GetMapping("/validate/{id}/{key}")
	public ResponseEntity<String> validate(@PathVariable Long id, @PathVariable String key) {
		if (service.validate(id, key)) {
			// worked fine
			return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
		}
		// something gone wrong....
		return new ResponseEntity<>(CmsConfig.RESPONSE_USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
	}
	
	private UserDTO parse(SignupDTO signup) {
		//@formatter:off
		return UserDTO.builder()
				.approved(false)
				.blocked(false)
				.displayName(signup.getDisplayName())
				.email(signup.getEmail())
				.failedAttempts(0)
				.idRole(Role.STANDARD.getId())
				.name(signup.getName())
				.password(signup.getPassword())
				.passwordConfirm(signup.getPasswordConfirm())
				.username(signup.getEmail())
				.validated(false)
				.build();
		//@formatter:on
	}
	
}
