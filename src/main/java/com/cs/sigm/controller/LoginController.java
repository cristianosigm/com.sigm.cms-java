package com.cs.sigm.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.adapter.domain.LoginDTO;
import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.domain.User;
import com.cs.sigm.domain.fixed.Role;
import com.cs.sigm.mapper.UserMapper;
import com.cs.sigm.service.LoginService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

	@Autowired
	private LoginService service;

	@Autowired
	private UserMapper mapper;

	@ApiOperation(code = 200, value = "Validate the provided Credentials and, if valid, creates a new back-end session.", response = UserDTO.class)
	@PostMapping
	public UserDTO login(final HttpServletResponse servletResponse, @Valid @RequestBody final LoginDTO login) {
		final User loggedUser = service.checkLogin(login);
		servletResponse.addCookie(new Cookie("userId", loggedUser.getId().toString()));
		servletResponse.addCookie(new Cookie("roleId", loggedUser.getIdRole().toString()));
		servletResponse.addCookie(new Cookie("roleName", Role.getKeyById(loggedUser.getIdRole())));
		return mapper.map(loggedUser);
	}

}
