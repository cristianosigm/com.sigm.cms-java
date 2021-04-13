package com.cs.sigm.controller;

import java.util.List;

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

import com.cs.sigm.adapter.domain.ContentDTO;
import com.cs.sigm.config.CmsConfig;
import com.cs.sigm.exception.CmsEntryNotFoundException;
import com.cs.sigm.mapper.ContentMapper;
import com.cs.sigm.service.ContentService;

@RestController
@RequestMapping(value = "/contents")
public class ContentController {

	@Autowired
	private ContentService service;

	@Autowired
	private ContentMapper mapper;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ContentDTO> findAll() {
		return mapper.mapResponse(service.findAll());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ContentDTO findSingle(@PathVariable Long id) {
		return mapper.map(service.findSingle(id).orElseThrow(() -> new CmsEntryNotFoundException("Content not found.")));
	}

	@PostMapping
	public ResponseEntity<String> save(@Valid @RequestBody ContentDTO request) {
		service.save(mapper.map(request));
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		service.deleteSingle(id);
		return new ResponseEntity<>(CmsConfig.RESPONSE_SUCCESS, HttpStatus.OK);
	}

}
