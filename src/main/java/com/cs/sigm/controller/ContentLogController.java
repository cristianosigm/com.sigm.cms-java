package com.cs.sigm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.adapter.domain.ContentLogDTO;
import com.cs.sigm.mapper.ContentLogMapper;
import com.cs.sigm.service.ContentLogService;

@RestController
@RequestMapping(value = "/contentlogs")
public class ContentLogController {
	
	@Autowired
	private ContentLogService service;
	
	@Autowired
	private ContentLogMapper mapper;
	
	@GetMapping("/{idContent}")
	public List<ContentLogDTO> findAllByIdUser(@PathVariable Long idContent) {
		return mapper.mapResponse(service.findAllByIdContent(idContent));
	}
	
}
