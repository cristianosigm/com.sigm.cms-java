package com.cs.sigm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.adapter.domain.OperationLogDTO;
import com.cs.sigm.domain.fixed.Entity;
import com.cs.sigm.mapper.OperationLogMapper;
import com.cs.sigm.service.OperationLogService;

@RestController
@RequestMapping(value = "/logs")
public class OperationLogController {

	@Autowired
	private OperationLogService service;

	@Autowired
	private OperationLogMapper mapper;

	@GetMapping("/{entity}/{id}")
	public List<OperationLogDTO> findAllByIdentity(@PathVariable String entity, @PathVariable Long id) {
		return mapper.mapResponse(service.findLogEntry(id, Entity.findByKey(entity)));
	}

}
