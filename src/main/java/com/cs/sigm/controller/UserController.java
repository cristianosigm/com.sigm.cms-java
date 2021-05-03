package com.cs.sigm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.mapper.UserMapper;
import com.cs.sigm.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	// TODO: update security so an User can load and update his/her own profile, but
	// cannot list other user's data.

	@Autowired
	private UserService service;

	@Autowired
	private UserMapper mapper;

	@GetMapping
	public List<UserDTO> findAll() {
		return mapper.mapResponse(service.findAll());
	}

	@GetMapping("/{id}")
	public UserDTO findSingle(@PathVariable Long id) {
		return mapper.map(service.findSingle(id));
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@Valid @RequestBody UserDTO request, @AuthenticationPrincipal User authUser) {
		// TODO: set the admin user which performed the action.

		log.info(" >>>> Current user: " + authUser.toString());

		service.save(mapper.map(request), Operation.UPDATE, authUser.getUsername());
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> create(@Valid @RequestBody UserDTO request, @AuthenticationPrincipal User authUser) {
		// TODO: set the admin user which performed the action.

		log.info(" >>>> Current user: " + authUser.toString());

		service.save(mapper.map(request), Operation.CREATE, authUser.getUsername());
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id, @AuthenticationPrincipal User authUser) {
		log.info(" >>>> Current user: " + authUser.toString());
		service.deleteSingle(id, authUser.getUsername());
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}

}
