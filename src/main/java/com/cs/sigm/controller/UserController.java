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

import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.exception.CmsEntryNotFoundException;
import com.cs.sigm.mapper.UserMapper;
import com.cs.sigm.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
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
		// TODO: translate this message
		return mapper.map(service.findSingle(id).orElseThrow(() -> new CmsEntryNotFoundException("Not found")));
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> update(@Valid @RequestBody UserDTO request) {
		// TODO: set the admin user which performed the action.
		service.save(mapper.map(request), Operation.UPDATE, 1L);
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
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
	
}
