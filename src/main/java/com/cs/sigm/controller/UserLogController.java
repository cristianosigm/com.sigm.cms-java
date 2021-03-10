package com.cs.sigm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.adapter.domain.UserLogDTO;
import com.cs.sigm.mapper.UserLogMapper;
import com.cs.sigm.service.UserLogService;

@RestController
@RequestMapping(value = "/userlog")
public class UserLogController {

	@Autowired
	private UserLogService service;

	@Autowired
	private UserLogMapper mapper;

	@GetMapping("/{idUser}")
	public List<UserLogDTO> findAllByIdUser(@PathVariable Long idUser) {
		return mapper.mapResponse(service.findAllByUserId(idUser));
	}

}
