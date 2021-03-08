package com.cs.sigm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.adapter.domain.SignupDTO;
import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.domain.fixed.Role;
import com.cs.sigm.exception.EntryNotFoundException;
import com.cs.sigm.mapper.UserMapper;
import com.cs.sigm.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private UserMapper mapper;

	@GetMapping
	public List<UserDTO> findAll() {
		return mapper.mapResponse(service.findAll());
	}

	@GetMapping("/single/{id}")
	public UserDTO findSingle(@PathVariable Long id) {
		// TODO: translate this message
		return mapper.map(service.findSingle(id).orElseThrow(() -> new EntryNotFoundException("Not found")));
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody SignupDTO request) {
		// TODO: set the admin user which performed the action.
		service.save(mapper.map(parse(request)), Operation.SIGNUP, 1L);
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@Valid @RequestBody UserDTO request) {
		// TODO: set the admin user which performed the action.
		service.save(mapper.map(request), Operation.UPDATE, 1L);
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping("/reset/{email}")
	public ResponseEntity<String> pwreset(@PathVariable String email) {
		if (service.requestPwreset(email)) {
			// worked fine
			return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
		}
		// something gone wrong....
		return new ResponseEntity<>(CmsConfig.RESPONSE_USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
	}

	@PostMapping
	public ResponseEntity<String> create(@Valid @RequestBody UserDTO request) {
		// TODO: set the admin user which performed the action.
		service.save(mapper.map(request), Operation.CREATE, 1L);
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		service.deleteSingle(id);
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}

	private UserDTO parse(SignupDTO signup) {
		return UserDTO.builder().approved(false).blocked(false).displayName(signup.getDisplayName())
				.email(signup.getEmail()).failedAttempts(0).idRole(Role.STANDARD.getId()).name(signup.getName())
				.password(signup.getPassword()).username(signup.getUsername()).validated(false).build();
	}

}
